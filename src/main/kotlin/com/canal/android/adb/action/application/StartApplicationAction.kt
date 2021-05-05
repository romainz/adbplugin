package com.canal.android.adb.action.application

class StartApplicationAction : BaseApplicationShellAction(DIALOG_TITLE, DIALOG_BUTTON) {

    override fun getShellCommand(application: String): String =
        "monkey -p $application -c android.intent.category.LAUNCHER 1"

    private companion object {
        const val DIALOG_TITLE = "Start application"
        const val DIALOG_BUTTON = "Start"
    }
}

