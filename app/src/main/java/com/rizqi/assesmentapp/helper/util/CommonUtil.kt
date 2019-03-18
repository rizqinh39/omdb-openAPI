package com.rizqi.assesmentapp.helper.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object CommonUtil {

    fun threshold(): Int {
        return 10
    }

    fun isConnected(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (anInfo in info) {
                    if (anInfo.state == NetworkInfo.State.CONNECTED)
                        return true
                }
            }
        }
        return false
    }

}
