package com.zanon.android.adb.action.device

import com.intellij.openapi.actionSystem.AnActionEvent
import com.zanon.android.adb.util.toCurrentDevice

class AdbDisconnectAction : com.zanon.android.adb.action.BaseAdbAction() {

    private var ipAddress = ""

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        project?.toCurrentDevice()?.let { device ->
            ipAddress = device.serialNumber
            super.actionPerformed(event)
        }
    }

    override fun getAdbCommand(): String = "disconnect $ipAddress"
}