package com.canal.android.adb.action.connectivity

import com.canal.android.adb.action.BaseShellAction

class DisableMobileDataAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc data disable"
}

