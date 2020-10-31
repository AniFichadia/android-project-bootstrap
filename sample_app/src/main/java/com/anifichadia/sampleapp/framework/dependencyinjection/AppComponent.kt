package com.anifichadia.sampleapp.framework.dependencyinjection

import android.content.Context
import com.anifichadia.sample_sdk.WoofyQuizService
import com.anifichadia.sampleapp.WoofyQuizApplication
import dagger.Component
import javax.inject.Singleton

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        ServiceModule::class
    ]
)
interface AppComponent {

    fun inject(app: WoofyQuizApplication)


    @ApplicationContext
    fun getAppContext(): Context

    fun getWoofyQuizService(): WoofyQuizService
}
