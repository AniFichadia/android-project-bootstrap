package com.anifichadia.sampleapp

import android.app.Application
import com.anifichadia.bootstrap.app.shared.BlackHolePrintStream
import com.anifichadia.sampleapp.framework.dependencyinjection.AppComponent
import com.anifichadia.sampleapp.framework.dependencyinjection.AppModule
import com.anifichadia.sampleapp.framework.dependencyinjection.DaggerAppComponent
import com.anifichadia.sampleapp.framework.dependencyinjection.ServiceModule
import com.anifichadia.sampleapp.shared.AppConfiguration
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class WoofyQuizApplication : Application() {

    private val appModule: AppModule by lazy { AppModule(this) }
    private val serviceModule: ServiceModule by lazy { ServiceModule() }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(appModule)
            .serviceModule(serviceModule)
            .build()
    }

    @Inject
    lateinit var configuration: AppConfiguration


    override fun onCreate() {
        super.onCreate()

        setup()
    }

    private fun setup() {
        inject()

        applyConfiguration()
    }

    private fun inject() {
        appComponent.inject(this)
    }

    private fun applyConfiguration() {
        Timber.uprootAll()
        if (configuration.debug) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Send any output to a black hole. Helps preventing production operations of the app
            val blackHolePrintStream = BlackHolePrintStream()
            System.setOut(blackHolePrintStream)
            System.setErr(blackHolePrintStream)
        }
    }
}
