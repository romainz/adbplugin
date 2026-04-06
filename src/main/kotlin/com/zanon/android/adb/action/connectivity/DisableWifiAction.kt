package com.zanon.android.adb.action.connectivity

import com.zanon.android.adb.action.BaseShellAction

class DisableWifiAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi disable"

}

