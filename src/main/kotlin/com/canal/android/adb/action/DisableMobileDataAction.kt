package com.canal.android.adb.action

class DisableMobileDataAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc data disable"
}

