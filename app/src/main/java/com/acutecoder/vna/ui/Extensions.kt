package com.acutecoder.vna.ui

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by Bhuvaneshwaran
 *
 * 11:49 pm 02-02-2024
 * @author AcuteCoder
 */

fun Any?.log(tag: String) = Log.e(tag, "$this")

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()