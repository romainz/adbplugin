package com.zanon.android.adb.panel

import com.intellij.util.ui.JBUI
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.DeeplinkTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import org.jdesktop.swingx.VerticalLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

object DeeplinkPanel {

    fun build(
        sendShellCommand: (String) -> Unit
    ): JPanel {
        val textField = JTextField()
        val deeplinks = AdbPluginSettingsState.instance.deeplinks
        val tableModel = DeeplinkTableModel(deeplinks.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = deeplinks[row].command
                },
                doubleClick = { row ->
                    deeplinks[row].command?.let { deeplink -> sendShellCommand(buildAdbCommand(deeplink)) }
                }
            )
        }
        return JPanel(VerticalLayout(5)).apply {
            // Custom deeplinks
            add(JPanel(GridBagLayout()).apply {
                // label
                val constraints1 = GridBagConstraints().apply {
                    weightx = 0.0
                }
                add(JLabel("Command: "), constraints1)
                // text field
                val constraints2 = GridBagConstraints().apply {
                    fill = GridBagConstraints.HORIZONTAL
                    weightx = 1.0
                }
                textField.addActionListener { actionEvent ->
                    sendShellCommand(buildAdbCommand(actionEvent.actionCommand))
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
                            textField.text?.let { deeplink -> sendShellCommand(buildAdbCommand(deeplink)) }
                        }
                    }
                }
                add(button, constraints3)
                // table
                val constraints5 = GridBagConstraints().apply {
                    fill = GridBagConstraints.BOTH
                    gridwidth = GridBagConstraints.REMAINDER
                    insets = JBUI.insetsTop(5)
                    weighty = 1.0
                }
                val jScrollPane = JScrollPane(table)
                add(jScrollPane, constraints5)
            })
        }
    }

    private fun createButton(
        text: String,
        shellCommand: String,
        sendShellCommand: (String) -> Unit,
    ): JButton =
        JButton(text).apply {
            addActionListener { sendShellCommand(shellCommand) }
        }

    private fun buildAdbCommand(deeplink: String): String = "am start -a android.intent.action.VIEW -d $deeplink"

}
