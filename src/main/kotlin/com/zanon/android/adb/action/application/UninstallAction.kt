package com.zanon.android.adb.action.application

import com.android.ddmlib.IDevice
import com.intellij.openapi.actionSystem.AnActionEvent
import com.zanon.android.adb.util.dialog.ApplicationSelectionDialog
import com.zanon.android.adb.util.toCurrentDevice

class UninstallAction : com.zanon.android.adb.action.BaseAdbAction() {

    private lateinit var device: IDevice
    private lateinit var selectedApplicationId: String

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.toCurrentDevice()?.let { device ->
            this.device = device

            val dialog = ApplicationSelectionDialog(
                dialogTitle = DIALOG_TITLE,
                button = DIALOG_BUTTON,
                action = { applicationId, _ ->
                    selectedApplicationId = applicationId
                    super.actionPerformed(event)}
            )
            dialog.show()
        }
    }

    override fun getAdbCommand(): String = "-s ${device.serialNumber} uninstall $selectedApplicationId"

    private companion object {
        const val DIALOG_TITLE = "Uninstall application"
        const val DIALOG_BUTTON = "Uninstall"
    }

}

