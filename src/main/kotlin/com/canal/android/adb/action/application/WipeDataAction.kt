package com.canal.android.adb.action.application

class WipeDataAction : BaseApplicationShellAction(DIALOG_TITLE, DIALOG_BUTTON) {

    override fun getShellCommand(application: String): String = "pm clear $application"

    private companion object {
        const val DIALOG_TITLE = "Wipe application data"
        const val DIALOG_BUTTON = "Wipe data"
    }
}

