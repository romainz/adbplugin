package com.zanon.android.adb.action.connectivity

import com.zanon.android.adb.action.BaseShellAction

class EnableMobileDataAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc data enable"

}

