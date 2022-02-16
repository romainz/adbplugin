package com.zanon.android.adb.action.deeplink

import com.android.ddmlib.IDevice
import com.intellij.openapi.actionSystem.AnActionEvent
import com.zanon.android.adb.action.BaseAdbAction
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.dialog.BaseCloseDialogWrapper
import com.zanon.android.adb.util.tablemodel.DeeplinkTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import com.zanon.android.adb.util.toCurrentDevice
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

class DeeplinkCustomAction : BaseAdbAction() {

    private lateinit var selectedDeeplink: String
    private lateinit var device: IDevice

    override fun actionPerformed(event: AnActionEvent) {
        val eventProject = event.project
        eventProject?.toCurrentDevice()?.let { device ->
            this.device = device
        }
        val dialog = DeeplinkSelectionDialog { text ->
            selectedDeeplink = text
            super.actionPerformed(event)
        }
        dialog.show()
    }

    override fun getAdbCommand(): String =
        // remove "adb" command because it is added in BaseAdbAction class
        when {
            selectedDeeplink.startsWith("http", true) -> {
                String.format(BASIC_URL_DEEPLINK_COMMAND, device.serialNumber, selectedDeeplink)
            }
            else -> String.format(DEVICE_PARAMETER, device.serialNumber) + selectedDeeplink.removePrefix("adb ")
        }


    private companion object {
        const val DEVICE_PARAMETER = "-s %s "
        const val BASIC_URL_DEEPLINK_COMMAND = "-s %s shell am start -a android.intent.action.VIEW -d %s"
    }
}

private class DeeplinkSelectionDialog(val send: (String) -> Unit) : BaseCloseDialogWrapper() {

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
                    deeplinks[row].command?.let { deeplink -> send(deeplink) }
                }
            )
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
            textField.addActionListener{ actionEvent ->
                send(actionEvent.actionCommand)
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
                        textField.text?.let { deeplink -> send(deeplink) }
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

    override fun getPreferredFocusedComponent(): JComponent = textField

    private companion object {
        val PREFERRED_SIZE = Dimension(400, 200)
    }
}