package com.example.bingtoy.presentation.lock

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.bingtoy.R
import com.example.bingtoy.presentation.MainActivity

class LockScreenService : Service() {
    private val receiver = MyBroadcastReceiver()

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        // startForeground()로 시작된 알림은 서비스가 종료될 때까지 유지됨
        startForeground(RECEIVER_NOTI_ID, createNotification(this))
        startBroadcastReceive()
        return START_STICKY
    }

    private fun startBroadcastReceive() {
        val filter =
            IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_ON)
                addAction(Intent.ACTION_SCREEN_OFF)
            }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter)
        } else {
            registerReceiver(receiver, filter, RECEIVER_EXPORTED)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("ㅌㅅㅌ", "서비스 onBind")
        return null
    }

    override fun onDestroy() {
        Log.d("ㅌㅅㅌ", "서비스 종료")
        unregisterReceiver(receiver)
        stopSelf()
        super.onDestroy()
    }

    companion object {
        const val RECEIVER_NOTI_ID = 1001
    }
}

fun createNotification(context: Context): Notification {
    val channelId = "잠금화면"
    val channelName = "잠금화면 감지 서비스"
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "화면 켜짐 감지를 위한 서비스 알림"
            }
        manager.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(context, channelId)
        .setContentTitle("잠금화면 사용 중")
        .setContentText("화면이 켜지는 것을 감지합니다.")
        .setSmallIcon(R.drawable.icon_tools)
        .setOngoing(true)
        .setAutoCancel(false)
        .setDeleteIntent(createDeletePendingIntent(context))
        .setContentIntent(createClickPendingIntent(context))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()
}

private fun createDeletePendingIntent(context: Context) =
    PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, NotificationDeleteReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

private fun createClickPendingIntent(context: Context): PendingIntent {
    val launchIntent =
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

    return PendingIntent.getActivity(
        context,
        0,
        launchIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
}
