package com.zanon.android.adb.panel

import com.intellij.util.ui.JBUI
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.DeeplinkTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class DeeplinkPanel(
    sendShellCommand: (String) -> Unit
) : RefreshableJPanel(GridBagLayout()) {

    private val tableModel = DeeplinkTableModel()

    init {
        val textField = JTextField()
        val deeplinks = AdbPluginSettingsState.instance.deeplinks
        tableModel.setDeeplinks(deeplinks)
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = tableModel.getDeeplink(row).command
                },
                doubleClick = { row ->
                    tableModel.getDeeplink(row).command?.let { deeplink -> sendShellCommand(buildAdbCommand(deeplink)) }
                }
            )
        }

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
            insets = JBUI.insetsTop(5)
            weighty = 1.0
        }
        val jScrollPane = JScrollPane(table).apply {
            horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        }
        add(jScrollPane, constraints5)
    }

    override fun refresh() {
        tableModel.setDeeplinks(AdbPluginSettingsState.instance.deeplinks)
    }

    private fun buildAdbCommand(deeplink: String): String = "am start -a android.intent.action.VIEW -d $deeplink"

}
