package com.anifichadia.app.service.retrofit.factory

import okhttp3.OkHttpClient

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
interface OkHttpClientFactory {
    fun createOkHttpClient(): OkHttpClient
}
