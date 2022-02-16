package com.zanon.android.adb.action.device

import com.intellij.openapi.actionSystem.AnActionEvent
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.dialog.BaseCloseDialogWrapper
import com.zanon.android.adb.util.tablemodel.DeviceTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*


abstract class BaseDeviceConnectionAction : com.zanon.android.adb.action.BaseAdbAction() {

    private var deviceIpAddress: String = ""

    override fun actionPerformed(event: AnActionEvent) {
        val dialog = DeviceSelectionDialog(
            sendDeviceIp = { deviceIp ->
                deviceIpAddress = deviceIp
                super.actionPerformed(event)
            }
        )
        dialog.show()
    }

    final override fun getAdbCommand(): String = getAdbCommand(deviceIpAddress)

    abstract fun getAdbCommand(ipAddress: String): String
}

private class DeviceSelectionDialog(
    val sendDeviceIp: (String) -> Unit
) : BaseCloseDialogWrapper() {

    private val textField = JTextField()

    init {
        init()
        title = "Select the device"
    }

    override fun createCenterPanel(): JComponent {
        val devices = AdbPluginSettingsState.instance.devices
        val tableModel = DeviceTableModel(devices.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { row ->
                    textField.text = devices[row].ip
                },
                doubleClick = { row ->
                    devices[row].ip?.let { ip ->
                        sendDeviceIp(ip)
                    }
                }
            )
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
            textField.addActionListener{ actionEvent ->
                sendDeviceIp(actionEvent.actionCommand)
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
                        sendDeviceIp(textField.text)
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
