package com.anifichadia.bootstrap.service

import com.anifichadia.bootstrap.service.TlsConfiguration.TLS
import com.anifichadia.bootstrap.service.client.HeaderEntryProvider
import com.anifichadia.bootstrap.service.client.UserAgentEntryProvider
import java.util.*

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
class ServiceConfiguration(
    val connectionConfiguration: ConnectionConfiguration,
    val connectionSecurityConfiguration: ConnectionSecurityConfiguration = ConnectionSecurityConfiguration(),

    val cacheConfiguration: CacheConfiguration = CacheConfiguration(),

    val headerConfiguration: HeaderConfiguration = HeaderConfiguration(),

    val locale: Locale = Locale.ENGLISH,
)

class ConnectionConfiguration(
    val baseUrl: String,

    val timeoutMs: Long = 15_000L,

    val maxConnectionPoolIdleConnections: Int = 20,
    val connectionKeepAliveTimeoutMs: Long = 30_000L,
)

class ConnectionSecurityConfiguration(
    val tlsConfiguration: Set<TlsConfiguration> = setOf(TLS),
    /** Null values indicate no specific certificate pinner will be configured */
    val certificatePins: Map<String, List<String>>? = null
)

enum class TlsConfiguration {
    TLS, CLEARTEXT
}

class CacheConfiguration(
    val enabled: Boolean = true,
    val cacheDirectory: String = "NetworkCache",
    val maxSizeInBytes: Long = 32 * 1024 * 1024 // 32 MiB
)

class HeaderConfiguration(
    val userAgentEntryProvider: UserAgentEntryProvider? = null,
    val headerEntryProvider: HeaderEntryProvider? = null
)
