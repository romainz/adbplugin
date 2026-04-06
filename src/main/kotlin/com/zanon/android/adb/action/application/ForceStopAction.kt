package com.zanon.android.adb.action.application

/**
 * Restart application when checkbox is selected
 */
class ForceStopAction : BaseApplicationShellAction(DIALOG_TITLE, DIALOG_BUTTON, CHECKBOX_TEXT) {

    override fun getShellCommand(application: String, isChecked: Boolean): String =
        if (isChecked) {
            "am force-stop $application && monkey -p $application -c android.intent.category.LAUNCHER 1"
        } else {
            "am force-stop $application"
        }

    private companion object {
        const val DIALOG_TITLE = "Force stop application"
        const val DIALOG_BUTTON = "Force stop"
        const val CHECKBOX_TEXT = "Restart application"
    }
}

