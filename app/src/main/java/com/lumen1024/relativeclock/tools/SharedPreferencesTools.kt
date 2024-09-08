package com.lumen1024.relativeclock.tools

import android.content.Context

fun Context.saveToPreferences(key: String, value: String) {
    val editor = this.getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit()
    editor.putString(key, value)
    editor.apply()
}

inline fun <reified T> Context.getFromPreferences(key: String) : T? {
    val prefs = this.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return when (T::class) {
        String::class -> prefs.getString(key, null) as T?
        else -> null
    }
}