package com.alxabr.auth.utils

import android.util.Log

private const val LOG_TAG = "AuthLogger"

object AuthLogger {

    fun error(message: String) {
        Log.e(LOG_TAG, message)
    }

    fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }
}