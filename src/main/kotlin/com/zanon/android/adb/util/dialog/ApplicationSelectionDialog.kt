package com.zanon.android.adb.util.dialog

import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.ApplicationTableModel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.table.JBTable
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class ApplicationSelectionDialog(dialogTitle: String, private val button: String) : DialogWrapper(true) {

    var selectedApplicationId: String? = null
    private val textField = JTextField()

    init {
        init()
        title = dialogTitle
    }

    override fun createCenterPanel(): JComponent {
        val applications = AdbPluginSettingsState.instance.applications
        val tableModel = ApplicationTableModel(applications.toMutableList())
        val table = JBTable(tableModel).apply {
            rowHeight = 22
            selectionModel.addListSelectionListener { listSelectionEvent ->
                selectedApplicationId = applications[listSelectionEvent.firstIndex].id
                close(OK_EXIT_CODE)
            }
        }
        textField.apply {
            document.addDocumentListener(object : DocumentListener {
                override fun insertUpdate(e: DocumentEvent?) {}

                override fun removeUpdate(e: DocumentEvent?) {}

                override fun changedUpdate(e: DocumentEvent?) {
                    selectedApplicationId = textField.selectedText
                }

            })
        }

        return JPanel(GridBagLayout()).apply {
            // label
            val constraints1 = GridBagConstraints().apply {
                weightx = 0.0
            }
            add(JLabel("Application id: "), constraints1)
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
            val button = JButton(button).apply {
                addActionListener {
                    if (textField.text.isNotEmpty()) {
                        selectedApplicationId = textField.text
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
        val PREFERRED_SIZE = Dimension(500, 200)
    }
}