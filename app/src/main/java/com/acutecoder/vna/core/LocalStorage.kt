package com.acutecoder.vna.core

import android.content.Context
import com.acutecoder.vna.data.Constants

/**
 * Created by Bhuvaneshwaran
 *
 * 11:43 pm 05-02-2024
 * @author AcuteCoder
 */

class LocalStorage(context: Context) {

    private val preferences = context.getSharedPreferences("preference", Context.MODE_PRIVATE)

    var speakingFormat: String
        get() = preferences.getString(
            Constants.KEY_SPEAKING_FORMAT,
            Constants.DEFAULT_SPEAKING_FORMAT
        )!!
        set(value) {
            preferences.edit().putString(Constants.KEY_SPEAKING_FORMAT, value).apply()
        }

    var speakingFormatAppend: String
        get() = preferences.getString(
            Constants.KEY_SPEAKING_FORMAT_APPEND,
            Constants.DEFAULT_SPEAKING_FORMAT_APPEND
        )!!
        set(value) {
            preferences.edit().putString(Constants.KEY_SPEAKING_FORMAT_APPEND, value).apply()
        }

}