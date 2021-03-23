package com.canal.android.adb.action.connectivity

import com.canal.android.adb.action.BaseShellAction

class EnableMobileDataAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc data enable"

}

