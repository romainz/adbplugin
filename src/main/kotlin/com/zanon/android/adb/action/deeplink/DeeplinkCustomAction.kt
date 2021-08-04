package com.zanon.android.adb.action.deeplink

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.table.JBTable
import com.zanon.android.adb.action.BaseAdbAction
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.DeeplinkTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class DeeplinkCustomAction : BaseAdbAction() {

    private lateinit var selectedDeeplink: String

    override fun actionPerformed(event: AnActionEvent) {
        val dialog = DeeplinkSelectionDialog()
        val dialogOk = dialog.showAndGet()
        if (dialogOk && dialog.selectedDeeplinkCommand != null) {
            selectedDeeplink = dialog.selectedDeeplinkCommand!!
            super.actionPerformed(event)
        }
    }


    override fun getAdbCommand(): String =
        // remove "adb" command because it is added in BaseAdbAction class
        selectedDeeplink.removePrefix("adb ")

}

private class DeeplinkSelectionDialog : DialogWrapper(true) {

    var selectedDeeplinkCommand: String? = null
    private val textField = JTextField()

    init {
        init()
        title = "Select the deeplink"
    }

    override fun createCenterPanel(): JComponent {
        val deeplinks = AdbPluginSettingsState.instance.deeplinks
        val tableModel = DeeplinkTableModel(deeplinks.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = deeplinks[row].command
                },
                doubleClick = { row ->
                    selectedDeeplinkCommand = deeplinks[row].command
                    close(OK_EXIT_CODE)
                }
            )
        }
        textField.apply {
            document.addDocumentListener(object : DocumentListener {
                override fun insertUpdate(e: DocumentEvent?) {}

                override fun removeUpdate(e: DocumentEvent?) {}

                override fun changedUpdate(e: DocumentEvent?) {
                    selectedDeeplinkCommand = textField.selectedText
                }

            })
        }

        return JPanel(GridBagLayout()).apply {
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
            add(textField, constraints2)
            // button
            val constraints3 = GridBagConstraints().apply {
                weightx = 0.0
                gridwidth = GridBagConstraints.REMAINDER
            }
            val button = JButton("Send").apply {
                addActionListener {
                    if (textField.text.isNotEmpty()) {
                        selectedDeeplinkCommand = textField.text
                        close(OK_EXIT_CODE)
                    }
                }
            }
            add(button, constraints3)
            // separator
            val constraints4 = GridBagConstraints().apply {
                fill = GridBagConstraints.HORIZONTAL
                gridwidth = GridBagConstraints.REMAINDER
                insets = Insets(5, 0, 5, 0)
            }
            add(JSeparator(SwingConstants.HORIZONTAL), constraints4)
            // table
            val constraints5 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                gridwidth = GridBagConstraints.REMAINDER
            }
            val jScrollPane = JScrollPane(table).apply {
                preferredSize = PREFERRED_SIZE
            }
            add(jScrollPane, constraints5)
            setResizable(false)
        }
    }

    private companion object {
        val PREFERRED_SIZE = Dimension(400, 200)
    }
}