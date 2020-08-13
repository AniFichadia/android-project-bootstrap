package com.anifichadia.app.shared.dependencyinjection

import android.content.Context
import com.anifichadia.app.AppConfiguration
import com.anifichadia.app.BuildConfig
import com.anifichadia.app.framework.dependencyinjection.ApplicationContext
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
            debug = BuildConfig.DEBUG
        )
}
