package com.zanon.android.adb.action.device

import android.view.KeyEvent
import com.android.ddmlib.IDevice
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.ui.IconManager
import com.intellij.ui.components.panels.VerticalLayout
import com.zanon.android.adb.action.BaseShellAction
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.dialog.BaseCloseDialogWrapper
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
) : BaseCloseDialogWrapper() {

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
        val panelNumbers = JPanel(GridBagLayout()).also { mainPanel ->
            // Numbers
            JButton("1").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_1) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            JButton("2").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_2) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            JButton("3").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_3) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            JButton("4").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_4) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            JButton("5").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_5) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            JButton("6").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_6) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            JButton("7").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_7) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            JButton("8").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_8) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            JButton("9").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_9) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            JButton("0").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_0) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
        }
        val panelDirections = JPanel(GridBagLayout()).also { mainPanel ->
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
        val panelPlayer= JPanel(GridBagLayout()).also { mainPanel ->
            // Play
            JButton("Play").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_MEDIA_PLAY) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            // Pause
            JButton("Pause").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_MEDIA_PAUSE) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            // Rewind
            JButton("RW").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_MEDIA_REWIND) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Fast forward
            JButton("FF").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Channel-
            JButton("CH-").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_CHANNEL_DOWN) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            // Channel+
            JButton("CH+").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_CHANNEL_UP) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            // Guide TV
            JButton("GUIDE").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_GUIDE) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
            // Info
            JButton("INFO").apply {
                addActionListener { sendKeyEvent(KeyEvent.KEYCODE_INFO) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
        }
        return JPanel(VerticalLayout(5)).apply {
            add(panelDirections)
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(panelNumbers)
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(panelPlayer)
        }
    }

    override fun getPreferredFocusedComponent(): JComponent = textField

    private companion object {
        val PREFERRED_SIZE = Dimension(600, 500)
    }
}