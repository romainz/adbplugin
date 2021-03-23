package com.canal.android.adb.action

class EnableOfflineModeAction : BaseShellAction() {

    override fun getShellCommand(): String = "svc wifi disable && svc data disable"

}

