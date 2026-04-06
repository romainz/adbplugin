package com.zanon.android.adb.panel

import com.android.ddmlib.IDevice
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTabbedPane
import org.jetbrains.android.sdk.AndroidSdkUtils
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.DefaultComboBoxModel
import javax.swing.JPanel

class AdbToolsPanel(
    private val project: Project
) : JPanel(BorderLayout()) {

    private val deviceSelectorModel = DefaultComboBoxModel<DeviceItem>()
    private val deviceSelector = ComboBox(deviceSelectorModel)

    val deviceTab: JPanel = createTabPanel("Device tab")
    val deeplinkTab: JPanel = createTabPanel("Deeplink tab")
    val applicationTab: JPanel = createTabPanel("Application tab")

    init {
        add(createHeaderPanel(), BorderLayout.NORTH)
        add(createTabbedPane(), BorderLayout.CENTER)

        refreshDevices()
    }

    fun refreshDevices() {
        deviceSelectorModel.removeAllElements()

        AndroidSdkUtils.getDebugBridge(project)?.devices
            ?.map { DeviceItem(it) }
            ?.forEach(deviceSelectorModel::addElement)
    }

    fun getSelectedDevice(): IDevice? = (deviceSelector.selectedItem as? DeviceItem)?.device

    private fun createHeaderPanel(): JPanel = JPanel(FlowLayout(FlowLayout.LEFT)).apply {
        add(JBLabel("Device"))
        add(deviceSelector)
    }

    private fun createTabbedPane(): JBTabbedPane = JBTabbedPane().apply {
        addTab("Device", deviceTab)
        addTab("Deeplink", deeplinkTab)
        addTab("Application", applicationTab)
    }

    private fun createTabPanel(title: String): JPanel = JBPanel<JBPanel<*>>(BorderLayout()).apply {
        add(JBLabel(title), BorderLayout.NORTH)
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
