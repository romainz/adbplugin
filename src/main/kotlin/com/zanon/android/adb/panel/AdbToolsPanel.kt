package com.zanon.android.adb.panel

import com.android.ddmlib.IDevice
import com.intellij.icons.AllIcons
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.JBUI
import com.zanon.android.adb.util.AdbCommandDelegate
import com.zanon.android.adb.util.ShellReceiver
import com.zanon.android.adb.util.showNotification
import org.jetbrains.android.sdk.AndroidSdkUtils
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JSeparator

class AdbToolsPanel(
    private val project: Project
) : JPanel(VerticalLayout(5)) {

    private val adbCommandDelegate: AdbCommandDelegate by lazy { AdbCommandDelegate() }
    private val deviceSelectorModel = DefaultComboBoxModel<DeviceItem>()
    private val deviceSelector = ComboBox(deviceSelectorModel)

    val deviceTab: JPanel = createTabPanel().apply {
        add(JBTabbedPane().apply {
            addTab("Connect", ConnectPanel.build(::sendAdbCommand))
            addTab("Input Text", InputTextPanel.build(::sendShellCommand))
            addTab("Remote", RemotePanel.build(::sendShellCommand))
            addTab("Other", OtherPanel.build(::sendShellCommand))
        })
    }
    val deeplinkTab: JPanel = createTabPanel().apply {
        add(DeeplinkPanel.build(::sendShellCommand))
    }
    val applicationTab: JPanel = createTabPanel().apply {
        add(ApplicationsPanel.build(::sendAdbCommand, ::sendShellCommand, ::showErrorNotification))
    }

    init {
        add(createHeaderPanel())
        add(JSeparator())
        add(createMainTabsPanel())

        refreshDevices()
    }

    private fun refreshDevices() {
        deviceSelectorModel.removeAllElements()

        AndroidSdkUtils.getDebugBridge(project)?.devices
            ?.map { DeviceItem(it) }
            ?.forEach(deviceSelectorModel::addElement)
    }

    fun getSelectedDevice(): IDevice? {
        return try {
            (deviceSelector.selectedItem as? DeviceItem)?.device
        } catch (_: Exception) {
            project.showNotification("Please select an online device and try again.", NotificationType.ERROR)
            null
        }
    }

    private fun createHeaderPanel(): JPanel = JPanel(GridBagLayout()).apply {
        add(JBLabel("Device"), GridBagConstraints().apply {
            gridx = 0
            gridy = 0
            insets = JBUI.insetsLeft(10)
        })
        add(deviceSelector, GridBagConstraints().apply {
            gridx = 1
            gridy = 0
            weightx = 1.0
            fill = GridBagConstraints.HORIZONTAL
            insets = JBUI.insets(0, 5)
        })
        add(JButton(AllIcons.Actions.Refresh).apply {
            toolTipText = "Refresh devices"
            addActionListener { refreshDevices() }
        }, GridBagConstraints().apply {
            gridx = 2
            gridy = 0
            insets = JBUI.insetsRight(10)
        })
    }

    private fun createMainTabsPanel(): JBTabbedPane = JBTabbedPane().apply {
        addTab("Device", deviceTab)
        addTab("Deeplink", deeplinkTab)
        addTab("Application", applicationTab)
    }

    private fun createTabPanel(): JPanel = JBPanel<JBPanel<*>>(BorderLayout())

    private data class DeviceItem(
        val device: IDevice
    ) {
        override fun toString(): String {
            return buildString {
                append(device.name)
                append(" (")
                append(device.serialNumber)
                append(")")
            }
        }
    }

    private fun showErrorNotification(message: String) {
        project.showNotification(message, NotificationType.ERROR)
    }

    private fun sendShellCommand(command: String) {
        val device: IDevice? = getSelectedDevice()
        if (device == null || device.isOffline) {
            showErrorNotification("Please select an online device and try again.")
        } else {
            device.executeShellCommand(
                command,
                ShellReceiver(project)
            )
        }
    }

    private fun sendAdbCommand(command: String) {
        adbCommandDelegate.sendAdbCommand(command, project)
    }
}
