package com.zanon.android.adb.action.device

import com.android.ddmlib.IDevice
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.zanon.android.adb.action.BaseShellAction
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.InputTextTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

class InputTextAction : BaseShellAction() {

    private var inputText: String = ""

    override fun process(project: Project, device: IDevice) {
        val dialog = InputTextSelectionDialog { text ->
            inputText = text
            super.process(project, device)
        }
        dialog.showAndGet()
    }

    override fun getShellCommand(): String = "input text $inputText"

}

private class InputTextSelectionDialog(val send: (String) -> Unit) : DialogWrapper(true) {

    private val textField = JTextField()

    init {
        init()
        title = "Select a text"
    }

    override fun createCenterPanel(): JComponent {
        val inputTexts = AdbPluginSettingsState.instance.inputTexts
        val tableModel = InputTextTableModel(inputTexts.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = inputTexts[row].text
                },
                doubleClick = { row ->
                    inputTexts[row].text?.let { text -> send(text) }
                }
            )
        }

        return JPanel(GridBagLayout()).apply {
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
            add(textField, constraints2)
            // button
            val constraints3 = GridBagConstraints().apply {
                weightx = 0.0
                gridwidth = GridBagConstraints.REMAINDER
            }
            val button = JButton("Send").apply {
                addActionListener {
                    if (textField.text.isNotEmpty()) {
                        send(textField.text)
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
        val PREFERRED_SIZE = Dimension(400, 500)
    }
}