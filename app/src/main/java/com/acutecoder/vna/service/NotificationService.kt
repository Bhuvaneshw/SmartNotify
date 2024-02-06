package com.acutecoder.vna.service

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.acutecoder.vna.core.VoiceEngine
import com.acutecoder.vna.data.NotificationData
import com.acutecoder.vna.ui.log

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

        val ticker = sbn.notification.tickerText?.toString()
        val text = extras.getCharSequence("android.text")?.toString()

        val ranking = Ranking()
        rankingMap.getRanking(sbn.key, ranking)

        val channel = ranking.channel
        val canAlert = channel.sound != null
                && channel.importance != NotificationManager.IMPORTANCE_LOW
                && channel.importance != NotificationManager.IMPORTANCE_MIN

        if (sbn.notification.flags.isCountable) {
            map[pack].let {
                if (it != null) {
                    it.count += 1
                    it.canAlert = it.canAlert || canAlert
                } else map[pack] =
                    NotificationData(
                        count = 1,
                        appName = sbn.appName(title),
                        title = title,
                        text = text,
                        ticker = ticker,
                        canAlert = canAlert
                    )
            }
        }

        VoiceEngine.speak(map, pack)

//        "Package: $pack".log("Service")
//        "Title: $title".log("Service")
//        "Text: $text".log("Service")
//        "Ticker: $ticker".log("Service")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        "Notification Removed".log("Service")
        sbn?.let { map.remove(it.packageName) }
    }

    private val Int.isCountable: Boolean
        get() = this and Notification.FLAG_GROUP_SUMMARY == 0

    private fun StatusBarNotification.appName(defaultName: String?): String? {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(appInfo) as String
        } catch (e: PackageManager.NameNotFoundException) {
            defaultName
        }
    }
}
