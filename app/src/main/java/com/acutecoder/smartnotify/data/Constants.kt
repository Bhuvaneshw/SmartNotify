package com.acutecoder.smartnotify.data

/**
 * Created by Bhuvaneshwaran
 *
 * 12:43 am 06-02-2024
 * @author AcuteCoder
 */

object Constants {

    private const val DOLLAR = "$"
    val FORMATTING_FIELD_TEXT =
        """|Formatting Fields
           |   ${DOLLAR}formattedCount -> represents count of the notification including a/are
           |       Ex:
           |           1. a message
           |           2. 11 messages
           |
           |   ${DOLLAR}isOrAre
           |       Ex:
           |           1. is
           |           2. are
           |
           |   ${DOLLAR}fromAppName -> represents name of the app including from
           |       Ex: 
           |           1. from WhatsApp
           |           2. from Telegram
           |
           |   ${DOLLAR}title -> The title of the notification
           |
           |   ${DOLLAR}text -> The text/description of the notification
           |
           |   ${DOLLAR}ticker -> The ticker/summary of the notification
           """.trimMargin()

    const val KEY_SPEAKING_PREFIX = "speaking_prefix"
    const val KEY_SPEAKING_MESSAGE = "speaking_message"

    //        const val TEMP = "Sir there \$count \$fromAppName, with title \$title stating \$text and with ticker \$ticker"
    const val DEFAULT_SPEAKING_PREFIX = "Sir there \$isOrAre"
    const val DEFAULT_SPEAKING_MESSAGE = "\$formattedCount \$fromAppName"
}