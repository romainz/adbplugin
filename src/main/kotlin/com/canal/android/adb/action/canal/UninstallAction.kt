package com.canal.android.adb.action.canal

class UninstallAction : BaseApplicationAction() {

    override fun getShellCommand(application: String): String = "uninstall $application"

}

