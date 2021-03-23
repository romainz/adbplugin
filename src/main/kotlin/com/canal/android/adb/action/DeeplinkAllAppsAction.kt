package com.canal.android.adb.action

class DeeplinkAllAppsAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a android.intent.action.ALL_APPS"

}

