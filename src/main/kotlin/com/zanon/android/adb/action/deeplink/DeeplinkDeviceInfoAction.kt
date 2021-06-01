package com.zanon.android.adb.action.deeplink

import android.provider.Settings
import com.zanon.android.adb.action.BaseShellAction

class DeeplinkDeviceInfoAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a ${Settings.ACTION_DEVICE_INFO_SETTINGS}"

}

