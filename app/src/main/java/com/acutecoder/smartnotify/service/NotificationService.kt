package com.acutecoder.smartnotify.service

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.acutecoder.smartnotify.core.VoiceEngine
import com.acutecoder.smartnotify.data.NotificationData


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
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
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

    companion object {

        /**
         * Reason for deprecation
         * getRunningServices() will return caller's service as backward compatibility,
         * which is enough for determining running state of this service
         *
         * Reason for warning
         * the IDE is reporting that serviceClass.getName() is always not equal to
         * service.className, but that is working as expected
         */
        @Suppress("deprecation", "warnings")
        fun isActive(context: Context): Boolean {
            val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val serviceClass = NotificationService::class.java

            for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.getName() == service.service.className) {
                    return true
                }
            }

            return false
        }
    }
}
