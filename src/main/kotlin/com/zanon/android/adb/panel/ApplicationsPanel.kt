package com.zanon.android.adb.panel

import com.intellij.util.ui.WrapLayout
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.ApplicationTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import org.jdesktop.swingx.VerticalLayout
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*

object ApplicationsPanel {

    fun build(
        onApplicationDoubleClicked: (String, Boolean) -> Unit,
    ): JComponent {
        val textField = JTextField()
        // buttons
        val buttonsPanel = JPanel(WrapLayout(FlowLayout.LEFT)).apply {
            add(JButton("Install"))
            add(JButton("Start"))
            add(JButton("Force Stop"))
            add(JButton("Wipe Data"))
            add(JButton("Uninstall"))
        }
        val checkBox = JCheckBox("Restart application")
        val applications = AdbPluginSettingsState.instance.applications
        val tableModel = ApplicationTableModel(applications.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { rowIndex ->
                    textField.text = applications[rowIndex].id
                },
                doubleClick = { rowIndex ->
                    onApplicationDoubleClicked(applications[rowIndex].id, checkBox.isSelected)
                }
            )
        }
        checkBox.apply {
            text = "Restart application"
            isSelected = true
        }
        val textFieldPanel = JPanel(BorderLayout(5, 0)).apply {
            add(JLabel("Application id: "), BorderLayout.WEST)
            add(textField, BorderLayout.CENTER)
        }
        val contentPanel = JPanel().apply {
            layout = VerticalLayout(5)
            add(textFieldPanel)
            add(buttonsPanel)
            add(checkBox)
        }
        return JPanel(BorderLayout(0, 5)).apply {
            add(contentPanel, BorderLayout.NORTH)
            add(JScrollPane(table), BorderLayout.CENTER)
        }
    }
}
