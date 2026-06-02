package com.zanon.android.adb.panel

import com.intellij.util.ui.JBUI
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.DeviceTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class ConnectPanel(
    sendAdbCommand: (String) -> Unit
) : RefreshableJPanel(GridBagLayout()) {

    private val tableModel = DeviceTableModel()

    init {
        val textField = JTextField()
        val devices = AdbPluginSettingsState.instance.devices
        tableModel.setDevices(devices)
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = tableModel.getDevice(row).ip
                },
                doubleClick = { row ->
                    tableModel.getDevice(row).ip?.let { ip ->
                        sendAdbCommand(buildAdbCommand(ip))
                    }
                }
            )
        }

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
        textField.addActionListener { actionEvent ->
            sendAdbCommand(buildAdbCommand(actionEvent.actionCommand))
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
                    sendAdbCommand(buildAdbCommand(textField.text))
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
            weighty = 1.0
        }
        val jScrollPane = JScrollPane(table)
        add(jScrollPane, constraints5)
    }

    override fun refresh() {
        tableModel.setDevices(AdbPluginSettingsState.instance.devices)
    }

    private fun buildAdbCommand(ipAddress: String): String = "connect $ipAddress"
}
