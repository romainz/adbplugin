package com.canal.android.adb.util

import com.android.ddmlib.MultiLineReceiver

class SingleLineReceiver(private val action: (String) -> Unit) : MultiLineReceiver() {

    private var cancelled = false

    override fun processNewLines(lines: Array<out String>?) {
        lines?.getOrNull(0)?.let { line ->
            action(line)
            cancelled = true
        }
    }

    override fun isCancelled(): Boolean = cancelled
}