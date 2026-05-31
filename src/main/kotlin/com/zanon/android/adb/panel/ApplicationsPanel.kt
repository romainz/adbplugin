package com.zanon.android.adb.panel

import com.intellij.ui.components.JBTabbedPane
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.setting.model.Application
import org.jdesktop.swingx.VerticalLayout
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.*


object ApplicationsPanel {

    private val textFieldApplication = JTextField()
    private val checkBox = JCheckBox("Restart application")

    fun build(
        sendAdbCommand: (String) -> Unit,
        sendShellCommand: (String) -> Unit,
        showErrorNotification: (String) -> Unit,
    ): JComponent {
        val applications = AdbPluginSettingsState.instance.applications
        val applicationItems = applications.map { ApplicationItem(it) }.toTypedArray()
        val applicationComboBox = JComboBox(applicationItems).apply {
            isEditable = true
            renderer = ApplicationItemRenderer()
        }

        fun getSelectedApplicationId(): String = applicationComboBox.selectedItem?.toString() ?: ""

        val applicationPanel = JPanel(BorderLayout(5, 0)).apply {
            add(JLabel("Application id: "), BorderLayout.WEST)
            add(applicationComboBox, BorderLayout.CENTER)
        }

        val contentPanel = JPanel().apply {
            layout = VerticalLayout(5)
            add(applicationPanel)
            add(JBTabbedPane().apply {
                addTab(
                    "Actions",
                    ApplicationsActionsPanel.build(
                        sendAdbCommand,
                        sendShellCommand,
                        showErrorNotification,
                        ::getSelectedApplicationId
                    )
                )
                addTab("Input text", InputTextPanel.build(sendShellCommand))
                addTab("Deeplink", DeeplinkPanel.build(sendShellCommand))
            })
        }

        return JPanel(BorderLayout(0, 5)).apply {
            add(contentPanel, BorderLayout.NORTH)
        }
    }


    private data class ApplicationItem(
        val application: Application
    ) {
        override fun toString(): String = application.id
    }

    private class ApplicationItemRenderer : DefaultListCellRenderer() {
        override fun getListCellRendererComponent(
            list: JList<*>?,
            value: Any?,
            index: Int,
            isSelected: Boolean,
            cellHasFocus: Boolean
        ): Component {
            val component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            if (component is JLabel && value is ApplicationItem) {
                component.text = "${value.application.name} (${value.application.id})"
            }
            return component
        }
    }
}
