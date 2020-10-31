package com.anifichadia.sampleapp.framework.dependencyinjection

import android.content.Context
import com.anifichadia.sampleapp.shared.AppConfiguration
import com.anifichadia.bootstrap.service.ConnectionConfiguration
import com.anifichadia.bootstrap.service.ServiceConfiguration
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@Module
class AppModule(
    private val appContext: Context
) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Context = appContext

    @Provides
    @Singleton
    fun provideAppConfiguration(): AppConfiguration =
        AppConfiguration(
            debug = true,

            serviceConfiguration = ServiceConfiguration(
                connectionConfiguration = ConnectionConfiguration(
                    baseUrl = "https://dog.ceo/",
                ),
            )
        )

    @Provides
    @Singleton
    fun provideServiceConfiguration(appConfiguration: AppConfiguration): ServiceConfiguration =
        appConfiguration.serviceConfiguration
}
