package com.canal.android.adb.action.device

import com.canal.android.adb.action.BaseAdbAction
import com.canal.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnActionEvent

class AdbDisconnectAction : BaseAdbAction() {

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