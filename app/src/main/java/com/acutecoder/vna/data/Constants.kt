package com.acutecoder.vna.data

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
           |   ${DOLLAR}count -> represents count of the notification including a/are
           |       Ex: 
           |           Format:
           |               There \${'$'}{dol}count message
           |           Output:
           |               1. There is a message (when count equal to 0)
           |               2. There are 12 messages (when count not equal to 0)
           |
           |   ${DOLLAR}fromAppName -> represents name of the app including from
           |       Ex: 
           |           Format:
           |               New message ${DOLLAR}fromAppName
           |           Output:
           |               1. New message from WhatsApp (if name found)
           |               2. New message (if name not found)
           |
           |   ${DOLLAR}title -> The title of the notification
           |
           |   ${DOLLAR}text -> The text/description of the notification
           |
           |   ${DOLLAR}ticker -> The ticker/summary of the notification
           """.trimMargin()

    const val KEY_SPEAKING_FORMAT = "speaking_format"

    //        const val TEMP = "Sir there \$count \$fromAppName, with title \$title stating \$text and with ticker \$ticker"
    const val DEFAULT_SPEAKING_FORMAT = "Sir there \$count \$fromAppName"
}