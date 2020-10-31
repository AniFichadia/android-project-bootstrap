package com.anifichadia.bootstrap.service.retrofit.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * @author Aniruddh Fichadia
 * @date 2018-09-08
 */
class RetrieveFromCacheOnFailureInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            Timber.d("Call ${chain.request().url} failed with $e, retrying with cache")

            chain.proceed(
                chain
                    .request()
                    .newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .header(
                        HEADER_CACHE_CONTROL,
                        CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build()
                            .toString()
                    )
                    .build()
            )
        }
    }

    companion object {
        private const val HEADER_CACHE_CONTROL = "Cache-Control"
        private const val HEADER_PRAGMA = "Pragma"
    }
}
