package com.acutecoder.smartnotify.core

import android.content.Context
import com.acutecoder.smartnotify.data.Constants

/**
 * Created by Bhuvaneshwaran
 *
 * 11:43 pm 05-02-2024
 * @author AcuteCoder
 */

class LocalStorage(context: Context) {

    private val preferences = context.getSharedPreferences("preference", Context.MODE_PRIVATE)

    var speakingPrefix: String
        get() = preferences.getString(
            Constants.KEY_SPEAKING_PREFIX,
            Constants.DEFAULT_SPEAKING_PREFIX
        )!!
        set(value) {
            preferences.edit().putString(Constants.KEY_SPEAKING_PREFIX, value).apply()
        }

    var speakingMessage: String
        get() = preferences.getString(
            Constants.KEY_SPEAKING_MESSAGE,
            Constants.DEFAULT_SPEAKING_MESSAGE
        )!!
        set(value) {
            preferences.edit().putString(Constants.KEY_SPEAKING_MESSAGE, value).apply()
        }

    val speakingFormat: String
        get() = "$speakingPrefix $speakingMessage"

}