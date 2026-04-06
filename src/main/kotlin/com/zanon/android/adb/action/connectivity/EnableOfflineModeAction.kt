package com.zanon.android.adb.action.connectivity

import com.zanon.android.adb.action.BaseShellAction

class EnableOfflineModeAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi disable && svc data disable"

}

