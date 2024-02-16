package com.acutecoder.smartnotify.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.acutecoder.smartnotify.core.LocalStorage
import com.acutecoder.smartnotify.core.VoiceEngine
import com.acutecoder.smartnotify.screeen.NavGraphs
import com.acutecoder.smartnotify.service.NotificationService
import com.acutecoder.smartnotify.ui.theme.SmartNotifyTheme
import com.acutecoder.smartnotify.ui.theme.ThemeColors
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)

        val localStorage = LocalStorage(applicationContext)
        setContent {
            SmartNotifyTheme {
                Surface(
                    modifier = Modifier
                        .background(ThemeColors.background)
                        .navigationBarsPadding()
                        .background(ThemeColors.light)
                        .statusBarsPadding()
                        .imePadding()
                        .fillMaxSize(),
                    color = ThemeColors.background
                ) {
                    LocalStorageProvider = compositionLocalOf { localStorage }
                    CompositionLocalProvider(LocalStorageProvider provides localStorage) {
                        DestinationsNavHost(navGraph = NavGraphs.root)
                    }
                }
            }
        }

        VoiceEngine.init(this, localStorage)
    }

    override fun onStart() {
        super.onStart()
        isServiceRunning = NotificationService.isActive(this)
    }

    override fun onResume() {
        super.onResume()
        isServiceRunning = NotificationService.isActive(this)
    }

    companion object {
        var isServiceRunning by mutableStateOf(false)
    }
}

lateinit var LocalStorageProvider: ProvidableCompositionLocal<LocalStorage>
//    private set
