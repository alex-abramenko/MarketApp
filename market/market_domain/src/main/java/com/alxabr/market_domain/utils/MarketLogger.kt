package com.alxabr.auth_domain.utils

import android.util.Log

private const val LOG_TAG = "MarketLogger"

object MarketLogger {

    fun error(message: String) {
        Log.e(LOG_TAG, message)
    }

    fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }
}