package com.canal.android.adb.action.connectivity

import com.canal.android.adb.action.BaseShellAction

class DisableOfflineModeAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi enable && svc data enable"

}

