package com.canal.android.adb.action

import com.android.ddmlib.IDevice
import com.canal.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

abstract class BaseDeviceAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project

        project.toCurrentDevice { device ->
            device.process()
        }
    }

    abstract fun IDevice.process()

}