package com.canal.android.adb.action.device

import com.android.ddmlib.IDevice
import com.android.ddmlib.NullOutputReceiver
import com.canal.android.adb.action.BaseDeviceAction
import com.canal.android.adb.util.SingleLineReceiver

class ToggleLayoutBoundsAction : BaseDeviceAction() {

    override fun IDevice.process() {
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

