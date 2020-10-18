package com.anifichadia.sdknetworking.service

import com.anifichadia.sdknetworking.service.TlsConfiguration.TLS
import com.anifichadia.sdknetworking.service.client.HeaderEntryProvider
import com.anifichadia.sdknetworking.service.client.UserAgentEntryProvider
import java.util.concurrent.TimeUnit

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
class ServiceConfiguration(
    val connectionConfiguration: ConnectionConfiguration,
    val connectionSecurityConfiguration: ConnectionSecurityConfiguration,
    val cacheConfiguration: CacheConfiguration,
    val headerConfiguration: HeaderConfiguration
)

class ConnectionConfiguration(
    val baseUrl: String,

    val timeoutInSeconds: Long = 15,

    val maxConnectionPoolIdleConnections: Int = 20,
    val connectionKeepAliveTimeout: Long = 30,
    val connectionKeepAliveTimeoutUnits: TimeUnit = TimeUnit.SECONDS
)

class ConnectionSecurityConfiguration(
    val tlsConfiguration: Set<TlsConfiguration> = setOf(TLS),
    val certificatePins: Map<String, List<String>>? = null
)

enum class TlsConfiguration {
    TLS, CLEARTEXT
}

class CacheConfiguration(
    val enabled: Boolean = true,
    val cacheDirectory: String = "NetworkCache",
    val maxSizeInBytes: Long = 32 * 1024 * 1024 // 32 MB
)

class HeaderConfiguration(
    val userAgentEntryProvider: UserAgentEntryProvider? = null,
    val headerEntryProvider: HeaderEntryProvider? = null
)
