package com.zanon.android.adb.action.deeplink

import com.zanon.android.adb.action.BaseShellAction
import com.zanon.android.adb.android.Settings

class DeeplinkWifiAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a ${Settings.ACTION_WIFI_SETTINGS}"

}

