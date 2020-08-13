package com.anifichadia.app.shared.dependencyinjection

import android.content.Context
import com.anifichadia.app.BootstrapApplication
import com.anifichadia.app.framework.dependencyinjection.ApplicationContext
import dagger.Component
import javax.inject.Singleton

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(app: BootstrapApplication)


    @ApplicationContext
    fun getAppContext(): Context
}
