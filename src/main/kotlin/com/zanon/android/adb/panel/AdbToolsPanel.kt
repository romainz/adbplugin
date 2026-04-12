package com.zanon.android.adb.panel

import com.android.ddmlib.IDevice
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.components.panels.VerticalLayout
import org.jetbrains.android.sdk.AndroidSdkUtils
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.*

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
    val applicationTab: JPanel = createTabPanel()

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

    private fun createHeaderPanel(): JPanel = JPanel(WrapLayout(FlowLayout.LEFT)).apply {
        add(JBLabel("Device"))
        add(JButton(AllIcons.Actions.Refresh).apply {
            toolTipText = "Refresh devices"
            addActionListener { refreshDevices() }
        })
        add(deviceSelector)
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

    // Layout to have content resizable when the width is reduced
    private class WrapLayout(
        align: Int
    ) : FlowLayout(align) {

        override fun preferredLayoutSize(target: Container): Dimension = layoutSize(target, true)

        override fun minimumLayoutSize(target: Container): Dimension {
            val minimum = layoutSize(target, false)
            minimum.width -= hgap + 1
            return minimum
        }

        private fun layoutSize(target: Container, preferred: Boolean): Dimension {
            synchronized(target.treeLock) {
                val targetWidth = when {
                    target.width > 0 -> target.width
                    else -> Int.MAX_VALUE
                }

                val insets = target.insets
                val horizontalInsetsAndGap = insets.left + insets.right + hgap * 2
                val maxWidth = targetWidth - horizontalInsetsAndGap

                var dimension = Dimension(0, 0)
                var rowWidth = 0
                var rowHeight = 0

                for (index in 0 until target.componentCount) {
                    val component = target.getComponent(index)
                    if (!component.isVisible) {
                        continue
                    }

                    val componentSize = if (preferred) component.preferredSize else component.minimumSize
                    if (rowWidth + componentSize.width > maxWidth) {
                        addRow(dimension, rowWidth, rowHeight)
                        rowWidth = 0
                        rowHeight = 0
                    }

                    if (rowWidth != 0) {
                        rowWidth += hgap
                    }

                    rowWidth += componentSize.width
                    rowHeight = maxOf(rowHeight, componentSize.height)
                }

                addRow(dimension, rowWidth, rowHeight)

                dimension.width += horizontalInsetsAndGap
                dimension.height += insets.top + insets.bottom + vgap * 2

                var container = target.parent
                while (container != null) {
                    if (container is JScrollPane) {
                        dimension.width -= hgap + 1
                        break
                    }
                    container = container.parent
                }

                return dimension
            }
        }

        private fun addRow(dimension: Dimension, rowWidth: Int, rowHeight: Int) {
            dimension.width = maxOf(dimension.width, rowWidth)
            if (dimension.height > 0) {
                dimension.height += vgap
            }
            dimension.height += rowHeight
        }
    }
}
