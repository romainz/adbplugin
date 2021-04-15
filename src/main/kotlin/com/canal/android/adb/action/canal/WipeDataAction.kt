package com.canal.android.adb.action.canal

class WipeDataAction : BaseApplicationShellAction() {

    override fun getShellCommand(application: String): String = "pm clear $application"

}

