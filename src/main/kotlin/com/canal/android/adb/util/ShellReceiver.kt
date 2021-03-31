package com.canal.android.adb.util

import android.util.Log
import com.android.ddmlib.IShellOutputReceiver

class ShellReceiver : IShellOutputReceiver {

    override fun addOutput(data: ByteArray?, offset: Int, length: Int) {
        if (data != null) {
            Log.d(tag, String(data))
        }
    }

    override fun flush() {}

    override fun isCancelled(): Boolean {
        return false
    }

    companion object {
        private val tag: String = ShellReceiver::class.java.simpleName

        private val receiver = ShellReceiver()

        fun getReceiver(): IShellOutputReceiver = receiver
    }
}