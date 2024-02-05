package com.acutecoder.vna.service

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.acutecoder.vna.core.VoiceEngine
import com.acutecoder.vna.ui.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

/**
 * Created by Bhuvaneshwaran
 *
 * 11:49 pm 02-02-2024
 * @author AcuteCoder
 */

class NotificationService : NotificationListenerService() {

    private var context: Context? = null
    private var map = mutableMapOf<String, NotificationData>()

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onNotificationPosted(sbn: StatusBarNotification, rankingMap: RankingMap) {
        val extras = sbn.notification.extras
        val title = extras.getString("android.title")
        val pack = sbn.packageName

//        rankingMap.orderedKeys.forEach { key ->
        val ranking = Ranking()
        rankingMap.getRanking(sbn.key, ranking)

        val channel = ranking.channel
        val canAlert = channel.sound != null
                && channel.importance != NotificationManager.IMPORTANCE_LOW
                && channel.importance != NotificationManager.IMPORTANCE_MIN

        map[pack] = map.getOrDefault(pack, NotificationData()).let {
            it.copy(it.count + 1, it.alert || canAlert)
        }
//        }

        map.log("map")
        VoiceEngine.speak(map, pack, sbn.appName(title))

        val ticker = sbn.notification.tickerText?.toString()
        val text = extras.getCharSequence("android.text")?.toString()

//        sbn.notification.log("notification")
//        if (sbn.notification.flags.isAlerting) {
//            VoiceEngine.speak("Sir, there is a notification$app")
//        }

//        "Package: $pack".log("Service")
//        "Title: $title".log("Service")
//        "Text: $text".log("Service")
//        "Ticker: $ticker".log("Service")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        "Notification Removed".log("Service")
        sbn?.let { map.remove(it.packageName) }
    }

//    private val Int.isAlerting: Boolean
//        get() = this and Notification.FLAG_ONGOING_EVENT == 0
//                && this and Notification.FLAG_GROUP_SUMMARY == 0

    private fun StatusBarNotification.appName(defaultName: String?): String? {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(appInfo) as String
        } catch (e: PackageManager.NameNotFoundException) {
            defaultName
        }
    }
}

data class NotificationData(val count: Int = 0, val alert: Boolean = false) {
    private var collector: FlowCollector<Int>? = null
    private val flow: Flow<Int> = flow {
        collector = this
    }

    suspend fun emit(value: Int) = collector?.emit(value)

    suspend fun collect(block: (Int) -> Unit) = flow.collectLatest(block)
}
