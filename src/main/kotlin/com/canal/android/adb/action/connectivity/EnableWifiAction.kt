package com.canal.android.adb.action.connectivity

import com.canal.android.adb.action.BaseShellAction

class EnableWifiAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi enable"

}

