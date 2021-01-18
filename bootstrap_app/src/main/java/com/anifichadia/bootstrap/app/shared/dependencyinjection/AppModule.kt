package com.anifichadia.bootstrap.app.shared.dependencyinjection

import android.content.Context
import com.anifichadia.bootstrap.app.AppConfiguration
import com.anifichadia.bootstrap.app.BuildConfig
import com.anifichadia.bootstrap.app.framework.dependencyinjection.ApplicationContext
import com.anifichadia.bootstrap.service.ConnectionConfiguration
import com.anifichadia.bootstrap.service.ServiceConfiguration
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
@Module
class AppModule(
    private val appContext: Context
) {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Context = appContext


    /** Create this how you will. Maybe retrieve it from preferences? Whatever you need */
    @Provides
    @Singleton
    fun provideConfiguration(): AppConfiguration =
        AppConfiguration(
            debug = BuildConfig.DEBUG,

            serviceConfiguration = ServiceConfiguration(
                ConnectionConfiguration(
                    baseUrl = "https://placeholder.com/"
                )
            )
        )
}
