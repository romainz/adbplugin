package com.canal.android.adb.action.application

class StartApplicationAction : BaseApplicationShellAction() {

    override fun getShellCommand(application: String): String =
        "monkey -p $application -c android.intent.category.LAUNCHER 1"

}

