package com.canal.android.adb.action

class EnableMobileDataAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc data enable"

}

