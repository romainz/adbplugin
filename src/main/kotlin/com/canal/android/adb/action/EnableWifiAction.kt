package com.canal.android.adb.action

class EnableWifiAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi enable"

}

