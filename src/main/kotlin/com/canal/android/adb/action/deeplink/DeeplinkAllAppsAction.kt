package com.canal.android.adb.action.deeplink

import com.canal.android.adb.action.BaseShellAction

class DeeplinkAllAppsAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a android.intent.action.ALL_APPS"

}

