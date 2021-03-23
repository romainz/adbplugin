package com.canal.android.adb.action

import com.android.ddmlib.IDevice
import com.android.ddmlib.NullOutputReceiver
import com.canal.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class StandByAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project

        project.toCurrentDevice { device ->
            device.standBy()
        }
    }

    private fun IDevice.standBy() {
        this.executeShellCommand(
            "am start -n \"com.android.systemui/.Somnambulator\"",
            NullOutputReceiver()
        )
    }

}

