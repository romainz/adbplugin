package com.canal.android.adb.action.deeplink

import com.canal.android.adb.action.BaseShellAction

class DeeplinkDeviceInfoAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a android.settings.DEVICE_INFO_SETTINGS"

}

