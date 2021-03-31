package com.canal.android.adb.action.canal

class ForceStopAction : BaseApplicationAction() {

    override fun getShellCommand(application: String): String = "am force-stop $application"

}

