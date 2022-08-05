package com.example.boundservice

import android.content.ComponentName
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.boundservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = (p1 as? BoundService.LocalBinder) ?: return
            val boundService = binder.getService()
            boundService.textViewChanged = { text ->
                binding.outputTv.text = text.toString()
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.startServiceBtn.setOnClickListener { launchService() }

        bindService(BoundService.newIntent(this), serviceConnection, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    private fun launchService() {
        startService(BoundService.newIntent(this))
    }
}