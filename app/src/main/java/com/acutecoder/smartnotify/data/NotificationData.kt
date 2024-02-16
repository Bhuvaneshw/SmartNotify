package com.acutecoder.smartnotify.data

/**
 * Created by Bhuvaneshwaran
 *
 * 12:16 am 06-02-2024
 * @author AcuteCoder
 */

data class NotificationData(
    var count: Int = 0,
    var appName: String? = "",
    var title: String? = "",
    var text: String? = "",
    var ticker: String? = "",
    var canAlert: Boolean = false
) {
    companion object {
        fun sampleData(packageName: String, single: Boolean) =
            mutableMapOf(
                packageName to NotificationData(
                    if (single) 1 else 2, "Smart Notify",
                    "Sample title", "Sample description",
                    "Sample title and description", true
                )
            )
    }
}
