package com.zanon.android.adb.action.application

class WipeDataAction : BaseApplicationShellAction(DIALOG_TITLE, DIALOG_BUTTON, CHECKBOX_TEXT) {

    override fun getShellCommand(application: String, isChecked: Boolean): String =
        if (isChecked) {
            "pm clear $application && monkey -p $application -c android.intent.category.LAUNCHER 1"
        } else {
            "pm clear $application"
        }

    private companion object {
        const val DIALOG_TITLE = "Wipe application data"
        const val DIALOG_BUTTON = "Wipe data"
        const val CHECKBOX_TEXT = "Restart application"
    }
}

