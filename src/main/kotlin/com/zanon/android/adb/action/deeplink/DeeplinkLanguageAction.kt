package com.zanon.android.adb.action.deeplink

import android.provider.Settings
import com.zanon.android.adb.action.BaseShellAction

class DeeplinkLanguageAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a ${Settings.ACTION_LOCALE_SETTINGS}"

}

