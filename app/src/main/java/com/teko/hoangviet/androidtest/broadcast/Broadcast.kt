package com.teko.hoangviet.androidtest.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.teko.hoangviet.androidtest.extension.hasNetworkConnection
import com.teko.hoangviet.androidtest.rxbus.RxBus
import com.teko.hoangviet.androidtest.rxbus.RxEvent

class Broadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                context?.let {
                    if (it.hasNetworkConnection() == true) {
                        RxBus.publish(RxEvent.NetworkConnectedEvent())
                    }
                }
            }
        }
    }
}