package com.zanon.android.adb.panel

import com.android.ddmlib.IDevice
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.JBUI
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

    private val deviceSelectorModel = DefaultComboBoxModel<DeviceItem>()
    private val deviceSelector = ComboBox(deviceSelectorModel)

    val deviceTab: JPanel = createTabPanel().apply {
        add(JBTabbedPane().apply {
            addTab("Connect", ConnectPanel.build { })
            addTab("Input Text", InputTextPanel.build { })
            addTab("Remote", RemotePanel.build { })
        })
    }
    val deeplinkTab: JPanel = createTabPanel()
    val applicationTab: JPanel = createTabPanel().apply {
        add(ApplicationsPanel.build { string, bool -> })
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

    fun getSelectedDevice(): IDevice? = (deviceSelector.selectedItem as? DeviceItem)?.device

    private fun createHeaderPanel(): JPanel = JPanel(GridBagLayout()).apply {
        add(JBLabel("Device"), GridBagConstraints().apply {
            gridx = 0
            gridy = 0
            insets = JBUI.insetsLeft(10)
        })
        add(JButton(AllIcons.Actions.Refresh).apply {
            toolTipText = "Refresh devices"
            addActionListener { refreshDevices() }
        }, GridBagConstraints().apply {
            gridx = 1
            gridy = 0
        })
        add(deviceSelector, GridBagConstraints().apply {
            gridx = 2
            gridy = 0
            weightx = 1.0
            fill = GridBagConstraints.HORIZONTAL
            insets = JBUI.insetsRight(10)
        })
    }

    private fun createMainTabsPanel(): JBTabbedPane = JBTabbedPane().apply {
        addTab("Device", deviceTab)
        addTab("Deeplink", deeplinkTab)
        addTab("Application", applicationTab)
    }

    private fun createTabPanel(): JPanel = JBPanel<JBPanel<*>>(BorderLayout()).apply {
//        add(JBLabel(title), BorderLayout.NORTH)
    }

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
}
