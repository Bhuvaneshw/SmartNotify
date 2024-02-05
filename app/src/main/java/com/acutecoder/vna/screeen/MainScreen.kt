package com.acutecoder.vna.screeen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.acutecoder.vna.ui.toast
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

/**
 * Created by Bhuvaneshwaran
 *
 * 11:16 pm 02-02-2024
 * @author AcuteCoder
 */

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen() {
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        Row {
            Button(onClick = { context.showSettings() }) {
                Text(text = "Settings")
            }
            Button(onClick = { context.startService() }) {
                Text(text = "Start")
            }
        }
    }
}

private fun Context.showSettings() {
    toast("Settings")
    startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
}

private fun Context.startService() {
    val intent = Intent("android.service.notification.NotificationListenerService")
    intent.setComponent(
        ComponentName(
            packageName,
            "$packageName.service.NotificationService"
        )
    )
    startService(intent)
}
