package com.zanon.android.adb.action.device

import android.view.KeyEvent
import com.android.ddmlib.IDevice
import com.intellij.icons.AllIcons
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

    private var text: String = ""
    private var isKeyEvent = false

    override fun process(project: Project, device: IDevice) {
        val dialog = InputTextSelectionDialog(
            sendDeeplink = { text ->
                isKeyEvent = false
                this.text = text
                super.process(project, device)
            },
            sendKeyEvent = { keyCode ->
                isKeyEvent = true
                this.text = keyCode.toString()
                super.process(project, device)
            })
        dialog.showAndGet()
    }

    override fun getShellCommand(): String =
        when (isKeyEvent) {
            true -> "input keyevent $text"
            false -> "input text $text"
        }

}

private class InputTextSelectionDialog(
    val sendDeeplink: (String) -> Unit,
    val sendKeyEvent: (Int) -> Unit
) : DialogWrapper(true) {

    private val textField = JTextField()

    init {
        init()
        title = "Select a text"
    }

    override fun createCenterPanel(): JComponent {

        return JPanel(GridBagLayout()).apply {
            // table
            var constraints = GridBagConstraints().apply {
                weightx = 2.0
            }
            add(createLeftLayout(), constraints)
            // buttons
            constraints = GridBagConstraints().apply {
                weightx = 1.0
                insets = Insets(0, 10, 0, 0)
            }
            add(createRightPanel(), constraints)
        }
    }

    private fun createLeftLayout(): JPanel {

        val inputTexts = AdbPluginSettingsState.instance.inputTexts
        val tableModel = InputTextTableModel(inputTexts.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = inputTexts[row].text
                },
                doubleClick = { row ->
                    inputTexts[row].text?.let { text -> sendDeeplink(text) }
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
                        sendDeeplink(textField.text)
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
            setResizable(true)
        }
    }

    private fun createRightPanel(): JPanel {
        return JPanel(GridBagLayout()).also { mainPanel ->
            // Up
            JButton(AllIcons.General.ArrowUp).apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_DPAD_UP) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            // Left
            JButton(AllIcons.General.ArrowLeft).apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_DPAD_LEFT) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Ok
            JButton("Ok").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_ENTER) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Right
            JButton(AllIcons.General.ArrowRight).apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_DPAD_RIGHT) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Down
            JButton(AllIcons.General.ArrowDown).apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_DPAD_DOWN) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            // Back
            JButton("Back").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_BACK) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
        }
    }

    private companion object {
        val PREFERRED_SIZE = Dimension(600, 500)
    }
}