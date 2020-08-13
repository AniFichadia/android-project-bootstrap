package com.anifichadia.app.service.retrofit.interceptor

import com.anifichadia.app.service.connectivity.ConnectivityChecker
import okhttp3.CacheControl
import okhttp3.Request
import java.util.concurrent.TimeUnit


/**
 * @author Aniruddh Fichadia
 * @date 2018-07-10
 */
class OfflineCacheInterceptor(
    private val connectivityChecker: ConnectivityChecker
) : AbstractRequestDecoratingInterceptor() {

    override fun decorateRequest(originalRequest: Request, request: Request.Builder) {
        val cacheControl: CacheControl = if (connectivityChecker.isOnline()) {
            originalRequest.cacheControl
        } else {
            CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()
        }

        request
            .removeHeader(HEADER_PRAGMA)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
    }


    private companion object {
        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"
    }
}
