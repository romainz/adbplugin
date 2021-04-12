package com.canal.android.adb.setting

import com.canal.android.adb.setting.model.Device
import com.canal.android.adb.setting.view.ApplicationsPanel
import com.canal.android.adb.setting.view.DevicesPanel
import com.canal.android.adb.setting.view.EditApplicationDialog
import com.canal.android.adb.setting.view.EditDeviceDialog
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 *
 * took inspiration of FileTypeConfigurable
 */
class AdbPluginSettingsComponent :
    ApplicationsPanel.Controller,
    DevicesPanel.Controller {

    val panel: JPanel
    private val applicationsPanel = ApplicationsPanel(this)
    private val devicesPanel = DevicesPanel(this)

    private val displayAdbNotificationCheckbox = JBCheckBox("Display the ADB command in a notification")

    val preferredFocusedComponent: JComponent
        get() = displayAdbNotificationCheckbox

    var displayAdbNotification: Boolean
        get() = displayAdbNotificationCheckbox.isSelected
        set(newStatus) {
            displayAdbNotificationCheckbox.isSelected = newStatus
        }

    var applications: List<String>
        get() = applicationsPanel.getApplications()
        set(newApplications) {
            for (application in newApplications) {
                applicationsPanel.addApplication(application)
            }
        }

    var devices: List<Device>
        get() = devicesPanel.getDevices()
        set(newDevices) {
            for (device in newDevices) {
                devicesPanel.addDevice(device)
            }
        }

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(displayAdbNotificationCheckbox, 1)
            .addComponent(applicationsPanel, 1)
            .addComponent(devicesPanel, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun editApplication() {
        val item: String = applicationsPanel.getSelectedItem()
        editApplication(item)
    }

    override fun addApplication() {
        editApplication(null)
    }

    override fun removeApplication() {
        applicationsPanel.removeSelected()
    }

    private fun editApplication(application: String?) {
        val title = "application id"
        val dialog = EditApplicationDialog(application)
        val builder = DialogBuilder(applicationsPanel)
        builder.setPreferredFocusComponent(dialog.applicationTextField)
        builder.setCenterPanel(dialog.mainPanel)
        builder.setTitle(title)
        builder.showModal(true)
        if (builder.dialogWrapper.isOK) {
            val newApplication = dialog.applicationTextField.text ?: return
            if (application.isNullOrEmpty()) {
                // add
                applicationsPanel.addApplication(newApplication)
            } else {
                // edit - todo
            }
        }
    }

    override fun editDevice() {
        editDevice(devicesPanel.getSelectedItem())
    }

    override fun addDevice() {
        editDevice(null)
    }

    override fun removeDevice() {
        devicesPanel.removeSelected()
    }

    private fun editDevice(device: Device?) {
        val title = "Device"
        val dialog = EditDeviceDialog(device)
        val builder = DialogBuilder(devicesPanel)
        builder.setCenterPanel(dialog.mainPanel)
        builder.setTitle(title)
        builder.showModal(true)
        if (builder.dialogWrapper.isOK) {
            val deviceName = dialog.nameTextField.text ?: return
            val deviceIp = dialog.ipTextField.text ?: return
            if (deviceIp.isNotEmpty()) {
                if (device == null) {
                    // add
                    devicesPanel.addDevice(Device(deviceName, deviceIp))
                } else {
                    // edit - todo
                }
            }
        }
    }

}
