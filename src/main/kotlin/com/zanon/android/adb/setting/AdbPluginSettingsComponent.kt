package com.zanon.android.adb.setting

import com.intellij.openapi.ui.DialogBuilder
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import com.zanon.android.adb.setting.model.Application
import com.zanon.android.adb.setting.model.Device
import com.zanon.android.adb.setting.view.ApplicationsPanel
import com.zanon.android.adb.setting.view.DevicesPanel
import com.zanon.android.adb.setting.view.EditApplicationDialog
import com.zanon.android.adb.setting.view.EditDeviceDialog
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

    var applications: List<Application>
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
        val item: Application = applicationsPanel.getSelectedItem()
        editApplication(item)
    }

    override fun addApplication() {
        editApplication(null)
    }

    override fun removeApplication() {
        applicationsPanel.removeSelected()
    }

    private fun editApplication(application: Application?) {
        val title = "application id"
        val dialog = EditApplicationDialog(application)
        val builder = DialogBuilder(applicationsPanel)
        builder.setPreferredFocusComponent(dialog.nameTextField)
        builder.setCenterPanel(dialog.mainPanel)
        builder.setTitle(title)
        builder.showModal(true)
        if (builder.dialogWrapper.isOK) {
            val applicationName = dialog.nameTextField.text ?: return
            val applicationId = dialog.idTextField.text ?: return
            if (applicationId.isNotEmpty()) {
                if (application == null) {
                    // add
                    applicationsPanel.addApplication(Application(applicationName, applicationId))
                } else {
                    // edit
                    applicationsPanel.editApplication(Application(applicationName, applicationId))
                }
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
                    // edit
                    devicesPanel.editDevice(Device(deviceName, deviceIp))
                }
            }
        }
    }

}
