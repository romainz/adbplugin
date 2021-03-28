package com.canal.android.adb.action.device

import com.android.ddmlib.IDevice
import com.android.ddmlib.NullOutputReceiver
import com.canal.android.adb.action.BaseDeviceAction
import com.canal.android.adb.util.SingleLineReceiver
import com.intellij.openapi.project.Project

class ToggleLayoutBoundsAction : BaseDeviceAction() {

    override fun process(project: Project, device: IDevice) {
        device.executeShellCommand(
            "getprop debug.layout",
            SingleLineReceiver { firstLine ->
                val enable = firstLine.toBoolean().not()
                device.enableLayoutBounds(enable)
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

