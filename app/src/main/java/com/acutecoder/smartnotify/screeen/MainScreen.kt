package com.acutecoder.smartnotify.screeen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acutecoder.smartnotify.core.LocalStorage
import com.acutecoder.smartnotify.core.VoiceEngine
import com.acutecoder.smartnotify.data.Constants
import com.acutecoder.smartnotify.data.NotificationData
import com.acutecoder.smartnotify.ui.LocalStorageProvider
import com.acutecoder.smartnotify.ui.MainActivity
import com.acutecoder.smartnotify.ui.components.ActionIcon
import com.acutecoder.smartnotify.ui.components.LabeledTextField
import com.acutecoder.smartnotify.ui.openLink
import com.acutecoder.smartnotify.ui.openNotificationListenerSettings
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

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val localStorage = LocalStorageProvider.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val prefix = remember { mutableStateOf(localStorage.speakingPrefix) }
    val message = remember { mutableStateOf(localStorage.speakingMessage) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Smart Notify") },
                actions = {
                    ActionIcon(Icons.Default.Person) { context.openLink(Constants.URL_WEBSITE) }
                    ActionIcon(Icons.Default.Build) { context.openNotificationListenerSettings() }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) {
        LazyColumn(
            Modifier
                .padding(it)
                .padding(10.dp)
                .fillMaxSize()
        ) {
            serviceStatus()
            verticalGap()

            messageTemplate(prefix, message)
            item {
                Row(Modifier.fillMaxSize()) {
                    CenterButton("Restore", Modifier.weight(1f)) {
                        localStorage.speakingPrefix = Constants.DEFAULT_SPEAKING_PREFIX
                        localStorage.speakingMessage = Constants.DEFAULT_SPEAKING_MESSAGE
                        prefix.value = Constants.DEFAULT_SPEAKING_PREFIX
                        message.value = Constants.DEFAULT_SPEAKING_MESSAGE
                        context.toast("Restored")
                    }
                    CenterButton("Save", Modifier.weight(1f)) {
                        localStorage.speakingPrefix = prefix.value
                        localStorage.speakingMessage = message.value
                        context.toast("Saved")
                    }
                }
            }

            verticalGap()
            voiceCheck()

            verticalGap()
            formattingFields()
        }
    }
}

private fun LazyListScope.serviceStatus() {
    titleItem("Service status")
    item {
        val context = LocalContext.current
        val isRunning = MainActivity.isServiceRunning

        Text(if (isRunning) "Running" else "Not Running", Modifier.padding(horizontal = 14.dp))
        if (!isRunning) {
            Spacer(Modifier.height(10.dp))
            CenterButton("Enable Service") { context.openNotificationListenerSettings() }
        }
    }
}

private fun LazyListScope.messageTemplate(
    prefix: MutableState<String>,
    message: MutableState<String>
) {
    titleItem("Template")
    item {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(15.dp))
            LabeledTextField("Prefix", prefix, Modifier.fillMaxWidth(0.9f))
            Spacer(Modifier.height(15.dp))
            LabeledTextField("Message", message, Modifier.fillMaxWidth(0.9f))
            Spacer(Modifier.height(15.dp))
        }
    }
}

private fun LazyListScope.voiceCheck() {
    titleItem("Voice Test")
    item {
        val context = LocalContext.current
        var single by remember { mutableStateOf(false) }

        CenterButton("Speak") {
            single = !single
            VoiceEngine.speak(
                NotificationData.sampleData(context.packageName, single),
                context.packageName
            )
        }
    }
}

private fun LazyListScope.formattingFields() {
    titleItem("Formatting fields")
    item {
        Text(Constants.FORMATTING_FIELD_TEXT, Modifier.padding(12.dp))
    }
}

private fun LazyListScope.verticalGap() {
    item { Spacer(Modifier.height(30.dp)) }
}

@Composable
private fun CenterButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(modifier = Modifier.fillMaxWidth(0.9f), onClick = onClick) {
            Text(text, color = ThemeColors.white)
        }
    }
}

private fun LazyListScope.titleItem(title: String) {
    item {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
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