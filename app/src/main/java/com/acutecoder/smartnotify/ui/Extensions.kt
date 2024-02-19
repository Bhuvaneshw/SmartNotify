package com.acutecoder.smartnotify.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.Modifier

/**
 * Created by Bhuvaneshwaran
 *
 * 11:49 pm 02-02-2024
 * @author AcuteCoder
 */

fun Any?.log(tag: String) = Log.e(tag, "$this")

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Context.openNotificationListenerSettings() {
    startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
}

fun Context.openLink(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun Modifier.on(condition: Boolean, other: Modifier.() -> Modifier) =
    if (condition)
        other()
    else this