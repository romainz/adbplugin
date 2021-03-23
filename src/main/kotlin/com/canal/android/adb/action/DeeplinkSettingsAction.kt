package com.canal.android.adb.action

class DeeplinkSettingsAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -a android.settings.SETTINGS"

}

