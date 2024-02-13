package com.acutecoder.smartnotify.screeen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acutecoder.smartnotify.core.LocalStorage
import com.acutecoder.smartnotify.data.Constants
import com.acutecoder.smartnotify.ui.LabeledTextField
import com.acutecoder.smartnotify.ui.LocalStorageProvider
import com.acutecoder.smartnotify.ui.theme.SmartNotifyTheme
import com.acutecoder.smartnotify.ui.theme.ThemeColors
import com.acutecoder.smartnotify.ui.toast
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
    val localStorage = LocalStorageProvider.current

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val prefix = remember { mutableStateOf(localStorage.speakingPrefix) }
        val message = remember { mutableStateOf(localStorage.speakingMessage) }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                Text(Constants.FORMATTING_FIELD_TEXT, Modifier.padding(12.dp))
            }
        }

        Spacer(Modifier.height(15.dp))
        LabeledTextField("Prefix", prefix, Modifier.fillMaxWidth(0.9f))
        Spacer(Modifier.height(15.dp))
        LabeledTextField("Message", message, Modifier.fillMaxWidth(0.9f))
        Spacer(Modifier.height(15.dp))

        Button(modifier = Modifier.fillMaxWidth(0.9f), onClick = {
            localStorage.speakingPrefix = prefix.value
            localStorage.speakingMessage = message.value
            context.toast("Saved")
        }) {
            Text("Save", color = ThemeColors.white)
        }

        Button(modifier = Modifier.fillMaxWidth(0.9f), onClick = { context.showSettings() }) {
            Text("Settings", color = ThemeColors.white)
        }
    }
}

private fun Context.showSettings() {
    startActivity(
        Intent(
            Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS,
            Uri.parse("package:$packageName")
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun MainScreenPreview() {
    val localStorage = LocalStorage(LocalContext.current)
    LocalStorageProvider = compositionLocalOf { localStorage }
    CompositionLocalProvider(LocalStorageProvider provides localStorage) {
        SmartNotifyTheme {
            MainScreen()
        }
    }
}