package com.canal.android.adb.action.canal

import com.android.ddmlib.IDevice
import com.canal.android.adb.action.BaseShellAction
import com.canal.android.adb.util.dialog.ApplicationSelectionDialog
import com.intellij.openapi.project.Project

abstract class BaseApplicationShellAction : BaseShellAction() {

    private var application: String = ""

    override fun process(project: Project, device: IDevice) {
        val dialog = ApplicationSelectionDialog()
        val dialogOk = dialog.showAndGet()
        if (dialogOk && dialog.selectedApplication != null) {
            application = dialog.selectedApplication!!
            super.process(project, device)
        }
    }

    override fun getShellCommand(): String = getShellCommand(application)

    abstract fun getShellCommand(application: String): String
}