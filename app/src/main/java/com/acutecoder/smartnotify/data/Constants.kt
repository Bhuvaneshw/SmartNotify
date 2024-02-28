package com.acutecoder.smartnotify.data

/**
 * Created by Bhuvaneshwaran
 *
 * 12:43 am 06-02-2024
 * @author AcuteCoder
 */

object Constants {

    const val URL_WEBSITE = "https://acutecoder.netlify.app/projects/smartnotify"

    val FORMATTING_FIELD_TEXT =
        listOf(
            FieldInfo("formattedCount", "represents count of the notification", "a", "11"),
            FieldInfo("isOrAre", null, "is", "are"),
            FieldInfo(
                "addSIfRequired",
                "add the letter 's' for count not equal to 1\n" +
                        "Sample format:\n" +
                        "message\$addSIfRequired",
                "message",
                "messages"
            ),
            FieldInfo(
                "fromAppName",
                "represents name of the app including \"from\"",
                "from WhatsApp",
                "from Telegram"
            ),
            FieldInfo("title", "The title of the notification"),
            FieldInfo("text", "The text/description of the notification"),
            FieldInfo("ticker", "The ticker/summary of the notification"),
        )

    const val KEY_SPEAKING_PREFIX = "speaking_prefix"
    const val KEY_SPEAKING_MESSAGE = "speaking_message"

    const val DEFAULT_SPEAKING_PREFIX = "Sir there \$isOrAre"
    const val DEFAULT_SPEAKING_MESSAGE = "\$formattedCount message\$addSIfRequired \$fromAppName"
}