package com.zanon.android.adb.setting

import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.util.Disposer
import com.intellij.ui.TabbedPaneWrapper
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import com.zanon.android.adb.setting.model.Application
import com.zanon.android.adb.setting.model.Deeplink
import com.zanon.android.adb.setting.model.Device
import com.zanon.android.adb.setting.model.InputText
import com.zanon.android.adb.setting.view.*
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 *
 * took inspiration of FileTypeConfigurable
 */
class AdbPluginSettingsComponent :
    ApplicationsPanel.Controller,
    DevicesPanel.Controller,
    DeeplinksPanel.Controller,
    InputTextsPanel.Controller {

    val panel: JPanel
    private val applicationsPanel = ApplicationsPanel(controller = this, doubleClick = { editApplication() })
    private val devicesPanel = DevicesPanel(controller = this, doubleClick = { editDevice() })
    private val deeplinksPanel = DeeplinksPanel(controller = this, doubleClick = { editDeeplink() })
    private val inputTextsPanel = InputTextsPanel(controller = this, doubleClick = { editInputText() })

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

    var deeplinks: List<Deeplink>
        get() = deeplinksPanel.getDeeplinks()
        set(newDeeplinks) {
            for (deeplink in newDeeplinks) {
                deeplinksPanel.addDeeplink(deeplink)
            }
        }

    var inputTexts: List<InputText>
        get() = inputTextsPanel.getInputTexts()
        set(newInputText) {
            for (inputText in newInputText) {
                inputTextsPanel.add(inputText)
            }
        }

    init {
        val tabs = TabbedPaneWrapper(Disposer.newDisposable()).apply {
            addTab("Application", applicationsPanel)
            addTab("Device", devicesPanel)
            addTab("Deeplink", deeplinksPanel)
            addTab("Input Texts", inputTextsPanel)
        }
        val space = JPanel().apply {
            minimumSize = Dimension(0, 20)
        }

        panel = FormBuilder.createFormBuilder()
            .addComponent(space, 1)
            .addComponent(displayAdbNotificationCheckbox, 1)
            .addComponent(space, 1)
            .addComponentFillVertically(tabs.component, 0)
            .panel
    }

    // region Applications

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
        val title = "Application"
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

    // endregion

    // region Device

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

    // endregion

    // region Deeplink

    override fun editDeeplink() {
        editDeeplink(deeplinksPanel.getSelectedItem())
    }

    override fun addDeeplink() {
        editDeeplink(null)
    }

    override fun removeDeeplink() {
        deeplinksPanel.removeSelected()
    }

    private fun editDeeplink(deeplink: Deeplink?) {
        val title = "Deeplink"
        val dialog = EditDeeplinkDialog(deeplink)
        val builder = DialogBuilder(deeplinksPanel)
        builder.setCenterPanel(dialog.mainPanel)
        builder.setTitle(title)
        builder.showModal(true)
        if (builder.dialogWrapper.isOK) {
            val deeplinkName = dialog.nameTextField.text ?: return
            val deeplinkCommand = dialog.commandTextField.text ?: return
            if (deeplinkCommand.isNotEmpty()) {
                if (deeplink == null) {
                    // add
                    deeplinksPanel.addDeeplink(Deeplink(deeplinkName, deeplinkCommand))
                } else {
                    // edit
                    deeplinksPanel.editDeeplink(Deeplink(deeplinkName, deeplinkCommand))
                }
            }
        }
    }

    // endregion

    // region Input Text

    override fun editInputText() {
        editInputText(inputTextsPanel.getSelectedItem())
    }

    override fun addInputText() {
        editInputText(null)
    }

    override fun removeInputText() {
        inputTextsPanel.removeSelected()
    }

    private fun editInputText(inputText: InputText?) {
        val title = "Input Text"
        val dialog = EditInputTextDialog(inputText)
        val builder = DialogBuilder(inputTextsPanel)
        builder.setCenterPanel(dialog.mainPanel)
        builder.setTitle(title)
        builder.showModal(true)
        if (builder.dialogWrapper.isOK) {
            val name = dialog.nameTextField.text ?: return
            val text = dialog.textTextField.text ?: return
            if (name.isNotEmpty()) {
                if (inputText == null) {
                    // add
                    inputTextsPanel.add(InputText(name, text))
                } else {
                    // edit
                    inputTextsPanel.edit(InputText(name, text))
                }
            }
        }
    }

    // endregion
}
