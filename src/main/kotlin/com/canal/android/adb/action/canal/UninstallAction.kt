package com.canal.android.adb.action.canal

import com.android.ddmlib.IDevice
import com.canal.android.adb.action.BaseAdbAction
import com.canal.android.adb.util.dialog.ApplicationSelectionDialog
import com.canal.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnActionEvent

class UninstallAction : BaseAdbAction() {

    private lateinit var device: IDevice
    private lateinit var application: String

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.toCurrentDevice()?.let { device ->
            this.device = device

            val dialog = ApplicationSelectionDialog()
            val dialogOk = dialog.showAndGet()
            if (dialogOk && dialog.selectedApplication != null) {
                application = dialog.selectedApplication!!
                super.actionPerformed(event)
            }
        }
    }

    override fun getAdbCommand(): String = "-s ${device.serialNumber} uninstall $application"

}

