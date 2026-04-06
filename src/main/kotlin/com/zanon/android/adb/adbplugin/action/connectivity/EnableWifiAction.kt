package com.zanon.android.adb.action.connectivity

import com.zanon.android.adb.action.BaseShellAction

class EnableWifiAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi enable"

}

