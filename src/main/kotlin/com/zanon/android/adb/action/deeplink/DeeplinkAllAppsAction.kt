package com.zanon.android.adb.action.deeplink

import com.zanon.android.adb.action.BaseShellAction

class DeeplinkAllAppsAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a android.intent.action.ALL_APPS"

}

