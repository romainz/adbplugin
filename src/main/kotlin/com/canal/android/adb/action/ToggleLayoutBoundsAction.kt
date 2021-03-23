package com.canal.android.adb.action

import com.android.ddmlib.IDevice
import com.android.ddmlib.NullOutputReceiver
import com.canal.android.adb.util.SingleLineReceiver
import com.canal.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ToggleLayoutBoundsAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project

        project.toCurrentDevice { device ->
            device.toggleLayoutBounds()
        }
    }

    private fun IDevice.toggleLayoutBounds() {
        this.executeShellCommand(
            "getprop debug.layout",
            SingleLineReceiver { firstLine ->
                val enable = firstLine.toBoolean().not()
                enableLayoutBounds(enable)
            }
        )
    }

    private fun IDevice.enableLayoutBounds(enable: Boolean) {
        this.executeShellCommand(
            "setprop debug.layout $enable ; service call activity 1599295570",
            NullOutputReceiver()
        )
    }

}

