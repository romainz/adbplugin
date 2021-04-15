package com.canal.android.adb.action.application

class ForceStopAction : BaseApplicationShellAction() {

    override fun getShellCommand(application: String): String = "am force-stop $application"

}

