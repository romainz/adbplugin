package com.canal.android.adb.action

class DisableWifiAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi disable"

}

