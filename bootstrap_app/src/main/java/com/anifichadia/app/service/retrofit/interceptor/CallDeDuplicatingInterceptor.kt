package com.anifichadia.app.service.retrofit.interceptor

import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.CountDownLatch


/**
 * @author Aniruddh Fichadia
 * @date 2020-08-26
 */
open class CallDeDuplicatingInterceptor(
    private val callEquivalenceComparator: Comparator<Call> = DefaultCallComparator()
) : Interceptor {

    private val activeCallCounts: MutableMap<Call, Int> = HashMap()
    private val callLocks: MutableMap<Call, CountDownLatch> = HashMap()
    private val callResults: MutableMap<Call, CallResult> = HashMap()

    private val fieldLock = Any()


    override fun intercept(chain: Chain): Response {
        if (canDeDuplicateCall()) {
            val (identifiableCall, firstCallOfKind, lockForCall) = synchronized(fieldLock) {
                val call = chain.call()
                val existingEquivalentCall = activeCallCounts.keys.find { callEquivalenceComparator.compare(it, call) == 0 }
                val identifiableCall = existingEquivalentCall ?: call
                val firstCallOfKind = existingEquivalentCall == null

                val lockForCall = if (firstCallOfKind) {
                    // Create a lock to block other calls until this call completes
                    val lock = CountDownLatch(1)
                    callLocks[identifiableCall] = lock
                    lock
                } else {
                    // Use an existing lock
                    callLocks[identifiableCall]!!
                }

                // Track this call as being active
                activeCallCounts[identifiableCall] = (activeCallCounts[identifiableCall] ?: 0) + 1

                CallInfo(identifiableCall, firstCallOfKind, lockForCall)
            }

            if (firstCallOfKind) {
                // Execute call, store? and dispatch the call result
                try {
                    val response = chain.proceed(chain.request())
                    val storableResponse = createStorableResponse(response)
                    handleResultForFirstCallOfKind(identifiableCall, CallResult.Success(storableResponse))

                    return response
                } catch (ex: IOException) {
                    handleResultForFirstCallOfKind(identifiableCall, CallResult.Error(ex))

                    throw ex
                } finally {
                    // Count down the lock to unblock any other pending calls
                    lockForCall.countDown()
                }
            } else {
                // Wait for the first call to complete
                lockForCall.await()

                return handleResultForDeDuplicatedCall(identifiableCall)
            }
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun handleResultForFirstCallOfKind(call: Call, result: CallResult) {
        synchronized(fieldLock) {
            val otherPendingCalls = activeCallCounts[call]!! - 1
            if (otherPendingCalls > 0) { // Other calls waiting, store result
                activeCallCounts[call] = otherPendingCalls

                callResults[call] = result
            } else { // No waiting calls, discard result
                activeCallCounts.remove(call)
            }

            callLocks.remove(call)
        }
    }

    @Throws(IOException::class)
    private fun handleResultForDeDuplicatedCall(identifiableCall: Call): Response {
        val result = synchronized(fieldLock) {
            val result = callResults[identifiableCall]

            val otherPendingCalls: Int = activeCallCounts[identifiableCall]!! - 1
            if (otherPendingCalls > 0) {
                // Other pending calls, just decrement
                activeCallCounts[identifiableCall] = otherPendingCalls
            } else {
                // No more pending calls, dispose of everything
                activeCallCounts.remove(identifiableCall)
                callResults.remove(identifiableCall)
                callLocks.remove(identifiableCall)
            }

            result
        }

        return when (result) {
            is CallResult.Success -> createDispatchedResponse(result.response)
            is CallResult.Error -> throw result.ex
            else -> throw IOException(NullPointerException("No result for de-duplicated call"))
        }
    }


    /** By default, all calls can be de-duplicated*/
    open fun canDeDuplicateCall(): Boolean = true

    /** By default, use a cloned response to prevent it from being consumed*/
    open fun createStorableResponse(response: Response): Response = response.clone()

    /** By default, use a cloned response to prevent it from being consumed*/
    open fun createDispatchedResponse(response: Response): Response = response.clone()


    private data class CallInfo(
        val identifiableCall: Call,
        val firstCallOfKind: Boolean,
        val lockForCall: CountDownLatch
    )

    private sealed class CallResult {
        data class Success(
            val response: Response
        ) : CallResult()

        data class Error(
            val ex: Exception
        ) : CallResult()
    }
}

/**
 * Compares calls using only the [okhttp3.Request]:
 *  - [okhttp3.Request.method],
 *  - [okhttp3.Request.url],
 *  - [okhttp3.Request.body]
 *
 * Additional fields may be added to this by extending the comparison
 */
open class DefaultCallComparator : Comparator<Call> {
    override fun compare(lhs: Call, rhs: Call): Int {
        lhs.request().method.compareTo(rhs.request().method)
            .takeIf { it != 0 }
            ?.apply { return this }

        lhs.request().url.toString().compareTo(rhs.request().url.toString())
            .takeIf { it != 0 }
            ?.apply { return this }

        lhs.request().body.asString().compareTo(rhs.request().body.asString())
            .takeIf { it != 0 }
            ?.apply { return this }

        return 0
    }
}


fun okhttp3.RequestBody?.asString(): String {
    return this?.let {
        val buffer = Buffer()
        it.writeTo(buffer)

        val charset: Charset = it.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8

        return if (buffer.isPlaintext()) {
            buffer.readString(charset)
        } else {
            // TODO: stringify binary representation?
            "(binary" + it.contentLength() + "-byte body omitted)"
        }
    } ?: "null"
}

private fun okhttp3.Response.clone(): Response {
    return this.newBuilder()
        .body(this.body?.let {
            this.peekBody(Long.MAX_VALUE)
        })
        .build()
}

private fun okio.Buffer.isPlaintext(): Boolean {
    try {
        val prefix = Buffer()
        val byteCount = if (this.size < 64) this.size else 64
        this.copyTo(prefix, 0, byteCount)
        for (i in 0..15) {
            if (prefix.exhausted()) {
                break
            }
            val codePoint = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
        }
        return true
    } catch (e: EOFException) {
        return false // Truncated UTF-8 sequence.
    }
}
