package com.example.boundservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class BoundService : Service() {

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, BoundService::class.java)
        }
    }

    var textViewChanged: ((Int) -> Unit)? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutineScope.launch {
            for (i in Data.getDataList()) {
                delay(1000)
                textViewChanged?.invoke(i)
            }
            stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onBind(p0: Intent?): IBinder {
        return LocalBinder()
    }

    inner class LocalBinder : Binder() {

        fun getService() = this@BoundService
    }
}