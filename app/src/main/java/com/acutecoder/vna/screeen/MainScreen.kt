package com.acutecoder.vna.screeen

import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.acutecoder.services.ai.ui.theme.ThemeColors
import com.acutecoder.vna.data.Constants
import com.acutecoder.vna.ui.LocalStorageProvider
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
    val localStorage = LocalStorageProvider.current

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var format by remember { mutableStateOf(localStorage.speakingFormat) }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                Text(Constants.FORMATTING_FIELD_TEXT, Modifier.padding(12.dp))
            }
        }

        TextField(
            value = format,
            onValueChange = { format = it },
            placeholder = { Text("Speaking Format") },
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        Spacer(Modifier.height(15.dp))

        Button(modifier = Modifier.fillMaxWidth(0.85f), onClick = {
            localStorage.speakingFormat = format
            context.toast("Saved")
        }) {
            Text("Save", color = ThemeColors.white)
        }

        Button(modifier = Modifier.fillMaxWidth(0.85f), onClick = { context.showSettings() }) {
            Text("Settings", color = ThemeColors.white)
        }
    }
}

private fun Context.showSettings() {
    startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
}
