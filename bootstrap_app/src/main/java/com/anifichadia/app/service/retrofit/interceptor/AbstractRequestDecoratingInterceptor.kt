package com.anifichadia.app.service.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-20
 */
abstract class AbstractRequestDecoratingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val decoratedRequestBuilder = request.newBuilder()

        decorateRequest(request, decoratedRequestBuilder)

        return chain.proceed(decoratedRequestBuilder.build())
    }

    abstract fun decorateRequest(originalRequest: Request, request: Request.Builder)
}
