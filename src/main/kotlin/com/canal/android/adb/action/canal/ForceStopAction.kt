package com.canal.android.adb.action.canal

class ForceStopAction : BaseApplicationShellAction() {

    override fun getShellCommand(application: String): String = "am force-stop $application"

}

