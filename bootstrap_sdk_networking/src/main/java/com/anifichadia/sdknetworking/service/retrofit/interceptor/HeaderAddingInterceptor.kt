package com.anifichadia.sdknetworking.service.retrofit.interceptor

import com.anifichadia.sdknetworking.service.client.HeaderEntryProvider
import okhttp3.Request

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-20
 */
class HeaderAddingInterceptor(
    private val headerEntryProvider: HeaderEntryProvider
) : AbstractRequestDecoratingInterceptor() {

    override fun decorateRequest(originalRequest: Request, request: Request.Builder) {
        headerEntryProvider
            .provideEntries()
            .filter { !it.value.isNullOrEmpty() }
            .forEach { request.header(it.name, it.value!!) }
    }
}
