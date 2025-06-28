package com.example.infludeo.presentation.lock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        when (intent.action) {
            Intent.ACTION_SCREEN_ON -> {
                Log.d("ㅌㅅㅌ", "${intent.action} 화면 켜짐!")
                showLockScreen(context)
            }

            Intent.ACTION_SCREEN_OFF -> {
                Log.d("ㅌㅅㅌ", "${intent.action} 화면 꺼짐!")
            }
        }
    }

    private fun showLockScreen(context: Context) {
        context.startActivity(
            Intent(context, LockActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            },
        )
    }
}
