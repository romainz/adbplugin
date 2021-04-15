package com.canal.android.adb.action.application

import com.android.ddmlib.IDevice
import com.canal.android.adb.action.BaseAdbAction
import com.canal.android.adb.util.dialog.ApplicationSelectionDialog
import com.canal.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnActionEvent

class UninstallAction : BaseAdbAction() {

    private lateinit var device: IDevice
    private lateinit var applicationId: String

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.toCurrentDevice()?.let { device ->
            this.device = device

            val dialog = ApplicationSelectionDialog()
            val dialogOk = dialog.showAndGet()
            if (dialogOk && dialog.selectedApplicationId != null) {
                applicationId = dialog.selectedApplicationId!!
                super.actionPerformed(event)
            }
        }
    }

    override fun getAdbCommand(): String = "-s ${device.serialNumber} uninstall $applicationId"

}

