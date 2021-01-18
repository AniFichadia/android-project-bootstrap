package com.anifichadia.bootstrap.app.shared

import android.content.Context
import java.util.*


/**
 * @author Aniruddh Fichadia
 * @date 2020-11-28
 */
object LocaleManager {
    val defaultLocale = Locale("en_us")


    fun setLocale(context: Context, locale: Locale = defaultLocale) {
        Locale.setDefault(locale)
        
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(defaultLocale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
