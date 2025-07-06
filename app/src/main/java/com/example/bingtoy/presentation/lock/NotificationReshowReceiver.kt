package com.example.bingtoy.presentation.lock

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat

class NotificationReshowReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        val notification = createNotification(context)
        NotificationManagerCompat.from(context)
            .notify(1234, notification)
    }
}
