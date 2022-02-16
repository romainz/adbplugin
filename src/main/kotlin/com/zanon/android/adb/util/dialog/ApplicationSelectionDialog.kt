package com.zanon.android.adb.util.dialog

import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.ApplicationTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import javax.swing.*

class ApplicationSelectionDialog(
    dialogTitle: String,
    private val button: String,
    private val action: (String, Boolean) -> Unit,
    private val checkBoxTitle: String? = null // set null to hide the checkbox
) : BaseCloseDialogWrapper() {

    private val checkBox = JCheckBox()
    private val textField = JTextField()

    init {
        init()
        title = dialogTitle
    }

    override fun createCenterPanel(): JComponent {
        val applications = AdbPluginSettingsState.instance.applications
        val tableModel = ApplicationTableModel(applications.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { rowIndex ->
                    textField.text = applications[rowIndex].id
                },
                doubleClick = { rowIndex ->
                    action(applications[rowIndex].id, checkBox.isSelected)
                }
            )
        }
        checkBox.apply {
            if (checkBoxTitle.isNullOrEmpty()) {
                isVisible = false
            } else {
                text = checkBoxTitle
                isSelected = true
            }
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
            textField.addActionListener{ actionEvent ->
                action(actionEvent.actionCommand, checkBox.isSelected)
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
                        action(textField.text, checkBox.isSelected)
                    }
                }
            }
            add(button, constraints3)
            // checkBox
            val constraints4 = GridBagConstraints().apply {
                fill = GridBagConstraints.HORIZONTAL
                gridwidth = GridBagConstraints.REMAINDER
                insets = Insets(5, 0, 0, 0)
            }
            add(checkBox, constraints4)
            // separator
            val constraints5 = GridBagConstraints().apply {
                fill = GridBagConstraints.HORIZONTAL
                gridwidth = GridBagConstraints.REMAINDER
                insets = Insets(5, 0, 5, 0)
            }
            add(JSeparator(SwingConstants.HORIZONTAL), constraints5)
            // table
            val constraints6 = GridBagConstraints().apply {
                fill = GridBagConstraints.BOTH
                gridwidth = GridBagConstraints.REMAINDER
            }
            val jScrollPane = JScrollPane(table).apply {
                preferredSize = PREFERRED_SIZE
            }
            add(jScrollPane, constraints6)
            setResizable(false)
        }
    }

    override fun getPreferredFocusedComponent(): JComponent = textField

    private companion object {
        val PREFERRED_SIZE = Dimension(500, 200)
    }
}