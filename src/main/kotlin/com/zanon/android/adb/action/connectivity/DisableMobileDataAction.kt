package com.zanon.android.adb.action.connectivity

import com.zanon.android.adb.action.BaseShellAction

class DisableMobileDataAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc data disable"
}

