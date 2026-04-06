package com.zanon.android.adb.util

import com.android.ddmlib.MultiLineReceiver
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

class ShellReceiver(val project: Project) : MultiLineReceiver() {

    private val output: StringBuilder = StringBuilder()
    private var notificationType = NotificationType.INFORMATION

    override fun flush() {}

    override fun isCancelled(): Boolean {
        return false
    }

    override fun processNewLines(lines: Array<out String>?) {
        for (line in lines!!) {
            processNewLine(line)
        }
        project.showNotification(output.toString(), notificationType, NOTIFICATION_ID)
    }

    private fun processNewLine(line: String) {
        output.append(line)
        when {
            line.startsWith(PREFIX_MESSAGE_ERROR) -> notificationType = NotificationType.ERROR
            line.startsWith(PREFIX_MESSAGE_WARNING) -> notificationType = NotificationType.WARNING
        }
    }


    private companion object {
        const val NOTIFICATION_ID = "shell_id"
        const val PREFIX_MESSAGE_ERROR = "Error"
        const val PREFIX_MESSAGE_WARNING = "Warning"
    }
}