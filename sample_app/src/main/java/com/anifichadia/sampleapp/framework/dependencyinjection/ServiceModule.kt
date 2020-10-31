package com.anifichadia.sampleapp.framework.dependencyinjection

import android.content.Context
import android.os.Build
import com.anifichadia.sample_sdk.WoofyQuizService
import com.anifichadia.sample_sdk.WoofyQuizServiceImpl
import com.anifichadia.sampleapp.shared.AppConfiguration
import com.anifichadia.bootstrap.service.ServiceConfiguration
import com.anifichadia.bootstrap.service.connectivity.AndroidLegacyConnectivityChecker
import com.anifichadia.bootstrap.service.connectivity.AndroidNPlusConnectivityChecker
import com.anifichadia.bootstrap.service.connectivity.ConnectivityChecker
import com.anifichadia.bootstrap.service.retrofit.factory.DefaultOkHttpClientFactory
import com.anifichadia.bootstrap.service.retrofit.factory.DefaultRetrofitFactory
import com.anifichadia.bootstrap.service.retrofit.factory.OkHttpClientFactory
import com.anifichadia.bootstrap.service.retrofit.factory.RetrofitFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@Module
class ServiceModule {

    // TODO: setup usages of this?
    @Provides
    @Singleton
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AndroidNPlusConnectivityChecker(context)
        } else {
            AndroidLegacyConnectivityChecker(context)
        }

    @Provides
    @Singleton
    fun provideOkHttpClientFactory(
        @ApplicationContext context: Context,
        serviceConfiguration: ServiceConfiguration,
    ): OkHttpClientFactory = DefaultOkHttpClientFactory(
        context,
        serviceConfiguration,
    )

    @Provides
    @Singleton
    fun provideRetrofitFactory(
        serviceConfiguration: ServiceConfiguration,
        okHttpClientFactory: OkHttpClientFactory
    ): RetrofitFactory = DefaultRetrofitFactory(
        serviceConfiguration,
        okHttpClientFactory
    )

    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitFactory: RetrofitFactory
    ): Retrofit = retrofitFactory.createRetrofit()


    @Provides
    @Singleton
    fun provideWoofyQuizService(
        retrofit: Retrofit,
        appConfiguration: AppConfiguration
    ): WoofyQuizService = WoofyQuizServiceImpl(appConfiguration.serviceConfiguration, retrofit)
}
