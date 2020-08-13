package com.anifichadia.app

import android.app.Application
import com.anifichadia.app.shared.BlackHolePrintStream
import com.anifichadia.app.shared.dependencyinjection.AppComponent
import com.anifichadia.app.shared.dependencyinjection.AppModule
import com.anifichadia.app.shared.dependencyinjection.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
class BootstrapApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(appModule)
            .build()
    }
    private val appModule: AppModule by lazy { AppModule(this) }

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
            // Send all output to a black hole to prevent viewing any logs
            System.setOut(BlackHolePrintStream())
            System.setErr(BlackHolePrintStream())
        }
    }
}
