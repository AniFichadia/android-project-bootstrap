package com.anifichadia.bootstrap.app.shared.dependencyinjection

import android.content.Context
import com.anifichadia.bootstrap.app.BootstrapApplication
import com.anifichadia.bootstrap.app.framework.dependencyinjection.ApplicationContext

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
//@Singleton
//@Component(
//    modules = [
//        AppModule::class
//    ]
//)
interface AppComponent {
    fun inject(app: BootstrapApplication)


    @ApplicationContext
    fun getAppContext(): Context
}
