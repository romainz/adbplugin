package com.canal.android.adb.action.connectivity

import com.canal.android.adb.action.BaseShellAction

class EnableOfflineModeAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi disable && svc data disable"

}

