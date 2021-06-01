package com.zanon.android.adb.action.application

import com.android.ddmlib.IDevice
import com.zanon.android.adb.action.BaseAdbAction
import com.zanon.android.adb.util.dialog.ApplicationSelectionDialog
import com.zanon.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnActionEvent

class UninstallAction : com.zanon.android.adb.action.BaseAdbAction() {

    private lateinit var device: IDevice
    private lateinit var applicationId: String

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.toCurrentDevice()?.let { device ->
            this.device = device

            val dialog = ApplicationSelectionDialog(DIALOG_TITLE, DIALOG_BUTTON)
            val dialogOk = dialog.showAndGet()
            if (dialogOk && dialog.selectedApplicationId != null) {
                applicationId = dialog.selectedApplicationId!!
                super.actionPerformed(event)
            }
        }
    }

    override fun getAdbCommand(): String = "-s ${device.serialNumber} uninstall $applicationId"

    private companion object {
        const val DIALOG_TITLE = "Uninstall application"
        const val DIALOG_BUTTON = "Uninstall"
    }

}

