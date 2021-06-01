package com.zanon.android.adb.action.application

class ForceStopAction : BaseApplicationShellAction(DIALOG_TITLE, DIALOG_BUTTON) {

    override fun getShellCommand(application: String): String = "am force-stop $application"

    private companion object {
        const val DIALOG_TITLE = "Force stop application"
        const val DIALOG_BUTTON = "Force stop"
    }
}

