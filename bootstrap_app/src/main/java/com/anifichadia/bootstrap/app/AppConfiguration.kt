package com.anifichadia.bootstrap.app

import com.anifichadia.bootstrap.service.ServiceConfiguration

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
data class AppConfiguration(
    val debug: Boolean = BuildConfig.DEBUG,

    val serviceConfiguration: ServiceConfiguration,
)
