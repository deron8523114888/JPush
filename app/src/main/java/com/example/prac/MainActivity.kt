package com.example.prac

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cn.jpush.android.api.JPushInterface

class MainActivity() : AppCompatActivity() {

    companion object {
        private var mMessageReceiver: MessageReceiver? = null
        var isForeground = false
        val MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION"
        val KEY_TITLE = "title"
        val KEY_MESSAGE = "message"
        val KEY_EXTRAS = "extras"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v("tag","onCreate")
        registerMessageReceiver()

        val bu = findViewById<Button>(R.id.button)
        bu.setOnClickListener(View.OnClickListener {


            Log.v("tag","onClick")
            JPushInterface.init(applicationContext)

        })
    }



    fun registerMessageReceiver() {
        mMessageReceiver = MessageReceiver()
        val filter = IntentFilter()
        filter.priority = IntentFilter.SYSTEM_HIGH_PRIORITY
        filter.addAction(MESSAGE_RECEIVED_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter)

    }


    override fun onResume() {
        isForeground = true
        super.onResume()
    }


    override fun onPause() {
        isForeground = false
        super.onPause()
    }


    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
    }
}

class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        Log.v("tag","onReceive")
        try {
            if (MainActivity.MESSAGE_RECEIVED_ACTION == intent.action) {
                val messge = intent.getStringExtra(MainActivity.KEY_MESSAGE)
                val extras = intent.getStringExtra(MainActivity.KEY_EXTRAS)
                val showMsg = StringBuilder()
                showMsg.append(MainActivity.KEY_MESSAGE + " : " + messge + "\n")
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(MainActivity.KEY_EXTRAS + " : " + extras + "\n")
                }
            }
        } catch (e: Exception) {
        }
    }
}
