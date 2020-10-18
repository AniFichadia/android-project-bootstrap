package com.anifichadia.sdknetworking.service.connectivity

import android.content.Context
import android.net.ConnectivityManager


/**
 * For Android versions N and above, use [AndroidNPlusConnectivityChecker]
 *
 * @author Aniruddh Fichadia
 * @date 2020-07-29
 */
class AndroidLegacyConnectivityChecker(
    context: Context
) : ConnectivityChecker {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun isOnline(): Boolean {
        return try {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            false
        }
    }
}
