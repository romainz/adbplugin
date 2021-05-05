package com.canal.android.adb.action.device

import com.canal.android.adb.action.BaseAdbAction
import com.canal.android.adb.setting.AdbPluginSettingsState
import com.canal.android.adb.util.tablemodel.DeviceTableModel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.table.JBTable
import java.awt.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


abstract class BaseDeviceConnectionAction : BaseAdbAction() {

    private var deviceIpAddress: String = ""

    override fun actionPerformed(event: AnActionEvent) {
        val dialog = DeviceSelectionDialog()
        val dialogOk = dialog.showAndGet()
        if (dialogOk && dialog.selectedDeviceIpAddress != null) {
            deviceIpAddress = dialog.selectedDeviceIpAddress!!
            super.actionPerformed(event)
        }
    }

    final override fun getAdbCommand(): String = getAdbCommand(deviceIpAddress)

    abstract fun getAdbCommand(ipAddress: String): String
}

private class DeviceSelectionDialog : DialogWrapper(true) {

    var selectedDeviceIpAddress: String? = null
    private val textField = JTextField()

    init {
        init()
        title = "Select the device"
    }

    override fun createCenterPanel(): JComponent {
        val devices = AdbPluginSettingsState.instance.devices
        val tableModel = DeviceTableModel(devices.toMutableList())
        val table = JBTable(tableModel).apply {
            rowHeight = 22
            selectionModel.addListSelectionListener { listSelectionEvent ->
                selectedDeviceIpAddress = devices[listSelectionEvent.firstIndex].ip
                close(OK_EXIT_CODE)
            }
        }
        textField.apply {
            document.addDocumentListener(object : DocumentListener {
                override fun insertUpdate(e: DocumentEvent?) {}

                override fun removeUpdate(e: DocumentEvent?) {}

                override fun changedUpdate(e: DocumentEvent?) {
                    selectedDeviceIpAddress = textField.selectedText
                }

            })
        }

        return JPanel(GridBagLayout()).apply {
            // label
            val constraints1 = GridBagConstraints().apply {
                weightx = 0.0
            }
            add(JLabel("Ip address: "), constraints1)
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
            val button = JButton("Connect").apply {
                addActionListener {
                    if (textField.text.isNotEmpty()) {
                        selectedDeviceIpAddress = textField.text
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
