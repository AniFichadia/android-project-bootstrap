package com.anifichadia.bootstrap.service.retrofit.interceptor

import com.anifichadia.bootstrap.service.client.UserAgentEntry
import com.anifichadia.bootstrap.service.client.UserAgentEntryProvider
import okhttp3.Request

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-20
 */
class UserAgentHeaderInterceptor(
    private val userAgentEntryProvider: UserAgentEntryProvider
) : AbstractRequestDecoratingInterceptor() {

    override fun decorateRequest(originalRequest: Request, request: Request.Builder) {
        val userAgentValue = userAgentEntryProvider
            .provideEntries()
            .filter { !it.value.isNullOrEmpty() }
            .map { UserAgentEntry(it.name.sanitise(), it.value!!.sanitise()) }
            .joinToString("") { "${it.name}=${it.value};" }

        request.header(HEADER_NAME_USER_AGENT, userAgentValue)
    }


    /** Replaces any invalid characters with a space (' ') character */
    private fun String.sanitise(): String = replace('\n', ' ')


    private companion object {
        const val HEADER_NAME_USER_AGENT = "User-Agent"
    }
}
