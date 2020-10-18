package com.anifichadia.sdknetworking.service.retrofit.factory

import android.content.Context
import com.anifichadia.sdknetworking.service.CacheConfiguration
import com.anifichadia.sdknetworking.service.ConnectionConfiguration
import com.anifichadia.sdknetworking.service.ConnectionSecurityConfiguration
import com.anifichadia.sdknetworking.service.HeaderConfiguration
import com.anifichadia.sdknetworking.service.ServiceConfiguration
import com.anifichadia.sdknetworking.service.TlsConfiguration
import com.anifichadia.sdknetworking.service.retrofit.interceptor.HeaderAddingInterceptor
import com.anifichadia.sdknetworking.service.retrofit.interceptor.TimberHttpLoggingInterceptorLogger
import com.anifichadia.sdknetworking.service.retrofit.interceptor.UserAgentHeaderInterceptor
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-08
 */
class DefaultOkHttpClientFactory(
    private val context: Context,
    private val serviceConfiguration: ServiceConfiguration,
    private val interceptors: List<Interceptor> = emptyList()
) : OkHttpClientFactory {

    override fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        configureConnection(builder, serviceConfiguration.connectionConfiguration)
        configureConnectionSecurity(builder, serviceConfiguration.connectionSecurityConfiguration)
        configureInterceptors(builder, interceptors)
        configureHeaders(builder, serviceConfiguration.headerConfiguration)
        configureCache(builder, serviceConfiguration.cacheConfiguration)

        return builder.build()
    }


    private fun configureConnection(
        builder: OkHttpClient.Builder,
        connectionConfiguration: ConnectionConfiguration
    ) {
        // Configure timeouts
        builder.readTimeout(connectionConfiguration.timeoutInSeconds, TimeUnit.SECONDS)
            .connectTimeout(connectionConfiguration.timeoutInSeconds, TimeUnit.SECONDS)

        // Configure the connection pool. Prevents failures due to the connection pool drying up
        builder.connectionPool(
            ConnectionPool(
                connectionConfiguration.maxConnectionPoolIdleConnections,
                connectionConfiguration.connectionKeepAliveTimeout,
                connectionConfiguration.connectionKeepAliveTimeoutUnits
            )
        )
    }

    private fun configureConnectionSecurity(
        builder: OkHttpClient.Builder,
        connectionSecurityConfiguration: ConnectionSecurityConfiguration
    ) {
        val connectionSpecs = connectionSecurityConfiguration
            .tlsConfiguration
            .distinct()
            .map {
                when (it) {
                    TlsConfiguration.TLS -> ConnectionSpec.MODERN_TLS
                    TlsConfiguration.CLEARTEXT -> ConnectionSpec.CLEARTEXT
                }
            }
        if (connectionSpecs.isEmpty()) throw IllegalStateException("No TLS configurations specified. Atleast one is required")
        builder.connectionSpecs(connectionSpecs)

        val certificatePins = connectionSecurityConfiguration.certificatePins
        if (!certificatePins.isNullOrEmpty()) {
            val certPinnerBuilder = CertificatePinner.Builder()
            certificatePins.forEach { (pattern, pins) ->
                certPinnerBuilder.add(pattern, *pins.toTypedArray())
            }
            builder.certificatePinner(certPinnerBuilder.build())
        }
    }

    private fun configureInterceptors(
        builder: OkHttpClient.Builder,
        interceptors: List<Interceptor>
    ) {
        builder.addNetworkInterceptor(
            HttpLoggingInterceptor(TimberHttpLoggingInterceptorLogger())
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        interceptors.forEach { interceptor -> builder.addInterceptor(interceptor) }
    }

    private fun configureHeaders(
        builder: OkHttpClient.Builder,
        headerConfiguration: HeaderConfiguration
    ) {
        val headerEntryProvider = headerConfiguration.headerEntryProvider
        if (headerEntryProvider != null) {
            builder.addInterceptor(HeaderAddingInterceptor(headerEntryProvider))
        }

        val userAgentEntryProvider = headerConfiguration.userAgentEntryProvider
        if (userAgentEntryProvider != null) {
            builder.addInterceptor(UserAgentHeaderInterceptor(userAgentEntryProvider))
        }
    }

    private fun configureCache(
        builder: OkHttpClient.Builder,
        cacheConfiguration: CacheConfiguration
    ) {
        if (cacheConfiguration.enabled) {
            val maxSize = cacheConfiguration.maxSizeInBytes
            val cacheDir = File(context.cacheDir.absolutePath, cacheConfiguration.cacheDirectory)
            val cache = Cache(cacheDir, maxSize)
            builder.cache(cache)
        }
    }
}
