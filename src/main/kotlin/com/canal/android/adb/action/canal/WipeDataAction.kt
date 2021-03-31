package com.canal.android.adb.action.canal

class WipeDataAction : BaseApplicationAction() {

    override fun getShellCommand(application: String): String = "pm clear $application"

}

