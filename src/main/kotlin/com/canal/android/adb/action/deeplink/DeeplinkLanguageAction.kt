package com.canal.android.adb.action.deeplink

import com.canal.android.adb.action.BaseShellAction

class DeeplinkLanguageAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a android.settings.LOCALE_SETTINGS"

}

