package com.acutecoder.smartnotify.data

/**
 * Created by Bhuvaneshwaran
 *
 * 12:43 am 06-02-2024
 * @author AcuteCoder
 */

object Constants {

    const val URL_WEBSITE = "https://acutecoder.netlify.app/projects/smartnotify"

    private const val DOLLAR = "$"
    val FORMATTING_FIELD_TEXT =
        """|${DOLLAR}formattedCount -> represents count of the notification
           |    Ex:
           |        1. a
           |        2. 11
           |
           |${DOLLAR}isOrAre
           |    Ex:
           |        1. is
           |        2. are
           |
           |${DOLLAR}addSIfRequired -> add the letter 's' for count not equal to 1
           |    Sample:
           |        message${DOLLAR}addSIfRequired
           |    Ex:
           |        1. message
           |        2. messages
           |
           |${DOLLAR}fromAppName -> represents name of the app including from
           |    Ex: 
           |        1. from WhatsApp
           |        2. from Telegram
           |
           |${DOLLAR}title -> The title of the notification
           |
           |${DOLLAR}text -> The text/description of the notification
           |
           |${DOLLAR}ticker -> The ticker/summary of the notification
           """.trimMargin()

    const val KEY_SPEAKING_PREFIX = "speaking_prefix"
    const val KEY_SPEAKING_MESSAGE = "speaking_message"

    const val DEFAULT_SPEAKING_PREFIX = "Sir there \$isOrAre"
    const val DEFAULT_SPEAKING_MESSAGE = "\$formattedCount message\$addSIfRequired \$fromAppName"
}