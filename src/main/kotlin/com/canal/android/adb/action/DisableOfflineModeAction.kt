package com.canal.android.adb.action

class DisableOfflineModeAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi enable && svc data enable"

}

