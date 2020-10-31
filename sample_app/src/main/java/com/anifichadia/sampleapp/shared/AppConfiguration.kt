package com.anifichadia.sampleapp.shared

import com.anifichadia.bootstrap.service.ServiceConfiguration

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
data class AppConfiguration(
    val debug: Boolean = false,

    val serviceConfiguration: ServiceConfiguration
)
