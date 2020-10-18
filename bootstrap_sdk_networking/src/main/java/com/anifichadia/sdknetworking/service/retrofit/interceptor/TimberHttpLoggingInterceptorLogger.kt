package com.anifichadia.sdknetworking.service.retrofit.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-20
 */
class TimberHttpLoggingInterceptorLogger : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        Timber.tag(logTag).d(message)
    }


    private companion object {
        const val logTag = "NetworkLog"
    }
}
