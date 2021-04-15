package com.canal.android.adb.action.application

class WipeDataAction : BaseApplicationShellAction() {

    override fun getShellCommand(application: String): String = "pm clear $application"

}

