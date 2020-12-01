package com.example.testapi.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.TimeUnit


/**
 * Helper class that emits items only when a specified timespan is passed.
 */
class Debouncer(
    private val timeout: Long,
    private val unit: TimeUnit,
    private val callback: (String) -> Unit
) {
    private val MESSAGE_WHAT = 684
    private val handler = Handler(Looper.getMainLooper()) { message ->
        if (message.what != MESSAGE_WHAT) {
            return@Handler false
        }
        callback(message.obj as String)
        true
    }


    fun process(text: String) {
        handler.removeMessages(MESSAGE_WHAT)
        val message = handler.obtainMessage(MESSAGE_WHAT, text)
        handler.sendMessageDelayed(message, unit.toMillis(timeout))
    }
}