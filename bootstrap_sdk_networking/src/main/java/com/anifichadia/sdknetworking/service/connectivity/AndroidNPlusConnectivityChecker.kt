package com.anifichadia.sdknetworking.service.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author Aniruddh Fichadia
 * @date 2018-07-19
 */
@RequiresApi(Build.VERSION_CODES.N)
class AndroidNPlusConnectivityChecker(
    context: Context
) : ConnectivityChecker {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback: ConnectivityManager.NetworkCallback = ConnectivityCheckerNetworkCallback()
    private var online: Boolean = false


    init {
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)

        online = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }


    override fun isOnline(): Boolean = online


    private inner class ConnectivityCheckerNetworkCallback : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            online = false
        }

        override fun onUnavailable() {
            super.onUnavailable()
            online = false
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            online = false
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            online = true
        }
    }
}
