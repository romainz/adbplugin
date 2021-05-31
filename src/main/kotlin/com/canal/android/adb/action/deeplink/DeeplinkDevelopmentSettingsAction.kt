package com.canal.android.adb.action.deeplink

import android.provider.Settings
import com.canal.android.adb.action.BaseShellAction

class DeeplinkDevelopmentSettingsAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a ${Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS}"

}

