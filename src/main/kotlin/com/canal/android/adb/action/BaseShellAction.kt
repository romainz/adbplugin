package com.canal.android.adb.action

import com.android.ddmlib.IDevice
import com.android.ddmlib.NullOutputReceiver

abstract class BaseShellAction : BaseDeviceAction() {

    override fun IDevice.process() {
        this.executeShellCommand(
            getShellCommand(),
            NullOutputReceiver()
        )
    }

    abstract fun getShellCommand(): String

}