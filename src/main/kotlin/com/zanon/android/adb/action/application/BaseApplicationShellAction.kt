package com.zanon.android.adb.action.application

import com.android.ddmlib.IDevice
import com.zanon.android.adb.action.BaseShellAction
import com.zanon.android.adb.util.dialog.ApplicationSelectionDialog
import com.intellij.openapi.project.Project

abstract class BaseApplicationShellAction(
    private val dialogTitle: String,
    private val dialogButton: String
) : BaseShellAction() {

    private lateinit var applicationId: String

    override fun process(project: Project, device: IDevice) {
        val dialog = ApplicationSelectionDialog(dialogTitle, dialogButton)
        val dialogOk = dialog.showAndGet()
        if (dialogOk && dialog.selectedApplicationId != null) {
            applicationId = dialog.selectedApplicationId!!
            super.process(project, device)
        }
    }

    override fun getShellCommand(): String = getShellCommand(applicationId)

    abstract fun getShellCommand(application: String): String
}