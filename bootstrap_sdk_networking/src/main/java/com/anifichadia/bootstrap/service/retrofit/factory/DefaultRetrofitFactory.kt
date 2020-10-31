package com.anifichadia.bootstrap.service.retrofit.factory

import com.anifichadia.bootstrap.service.ServiceConfiguration
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
class DefaultRetrofitFactory(
    private val serviceConfiguration: ServiceConfiguration,
    private val okHttpClientFactory: OkHttpClientFactory,
    private val converterFactory: Converter.Factory = GsonConverterFactory.create()
) : RetrofitFactory {

    override fun createRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(serviceConfiguration.connectionConfiguration.baseUrl)
            .client(okHttpClientFactory.createOkHttpClient())

        configureConverterFactory(builder)

        return builder.build()
    }

    private fun configureConverterFactory(builder: Retrofit.Builder) {
        builder.addConverterFactory(converterFactory)
    }
}
