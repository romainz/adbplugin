package com.zanon.android.adb.action.application

import com.android.ddmlib.IDevice
import com.intellij.openapi.project.Project
import com.zanon.android.adb.action.BaseShellAction
import com.zanon.android.adb.util.dialog.ApplicationSelectionDialog

abstract class BaseApplicationShellAction(
    private val dialogTitle: String,
    private val dialogButton: String,
    private val dialogCheckBoxTitle: String? = null // set null to hide the checkbox
) : BaseShellAction() {

    private lateinit var applicationId: String
    private var isChecked: Boolean = false

    override fun process(project: Project, device: IDevice) {
        val dialog = ApplicationSelectionDialog(
            dialogTitle = dialogTitle,
            button = dialogButton,
            action = { applicationId, isChecked ->
                this.applicationId = applicationId
                this.isChecked = isChecked
                super.process(project, device)
            },
            checkBoxTitle = dialogCheckBoxTitle
        )
        dialog.show()
    }

    override fun getShellCommand(): String = getShellCommand(applicationId, isChecked)

    open fun getShellCommand(application: String, isChecked: Boolean): String = getShellCommand(application)

    open fun getShellCommand(application: String): String = ""
}