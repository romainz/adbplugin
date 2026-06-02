package com.zanon.android.adb.panel

import com.intellij.util.ui.JBUI
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.InputTextTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class InputTextPanel(
    sendShellCommand: (String) -> Unit
) : RefreshableJPanel(GridBagLayout()) {

    private val tableModel = InputTextTableModel()

    init {
        val textField = JTextField()
        val inputTexts = AdbPluginSettingsState.instance.inputTexts
        tableModel.setInputTexts(inputTexts)
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = tableModel.get(row).text
                },
                doubleClick = { row ->
                    tableModel.get(row).text?.let { text -> sendShellCommand(buildShellCommand(text)) }
                }
            )
        }

        // label
        val constraints1 = GridBagConstraints().apply {
            weightx = 0.0
        }
        add(JLabel("Text: "), constraints1)
        // text field
        val constraints2 = GridBagConstraints().apply {
            fill = GridBagConstraints.HORIZONTAL
            weightx = 1.0
        }
        textField.addActionListener { actionEvent ->
            sendShellCommand(buildShellCommand(actionEvent.actionCommand))
        }
        add(textField, constraints2)
        // button
        val constraints3 = GridBagConstraints().apply {
            weightx = 0.0
            gridwidth = GridBagConstraints.REMAINDER
        }
        val button = JButton("Send").apply {
            addActionListener {
                if (textField.text.isNotEmpty()) {
                    sendShellCommand(buildShellCommand(textField.text))
                }
            }
        }
        add(button, constraints3)
        // separator
        val constraints4 = GridBagConstraints().apply {
            fill = GridBagConstraints.HORIZONTAL
            gridwidth = GridBagConstraints.REMAINDER
            insets = JBUI.insets(5, 0)
        }
        add(JSeparator(SwingConstants.HORIZONTAL), constraints4)
        // table
        val constraints5 = GridBagConstraints().apply {
            fill = GridBagConstraints.BOTH
            gridwidth = GridBagConstraints.REMAINDER
            weighty = 1.0
        }
        val jScrollPane = JScrollPane(table)
        add(jScrollPane, constraints5)
    }

    override fun refresh() {
        tableModel.setInputTexts(AdbPluginSettingsState.instance.inputTexts)
    }

    private fun buildShellCommand(text: String): String = "input text $text"
}
