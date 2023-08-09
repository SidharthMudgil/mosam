package com.sidharth.mosam.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkConnectivityCallback(
    private val onConnectionAvailable: () -> Unit,
    private val onConnectionLost: () -> Unit,
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        onConnectionAvailable()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        onConnectionLost()
    }
}

object NetworkUtils {

    private var connectivityCallback: NetworkConnectivityCallback? = null

    fun startNetworkCallback(
        context: Context,
        onConnectionAvailable: () -> Unit,
        onConnectionLost: () -> Unit,
    ) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

        val callback = NetworkConnectivityCallback(onConnectionAvailable, onConnectionLost)
        connectivityManager.registerNetworkCallback(networkRequest, callback)
        connectivityCallback = callback
    }

    fun stopNetworkCallback(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
            connectivityCallback = null
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && (
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }
}
