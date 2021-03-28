package com.canal.android.adb.util

import com.android.ddmlib.IDevice
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.table.JBTable
import org.jetbrains.android.sdk.AndroidSdkUtils
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.table.AbstractTableModel


fun Project?.toCurrentDevice(process: (Project, IDevice) -> Unit) {
    val devices = this?.let { AndroidSdkUtils.getDebugBridge(it)?.devices }
    when {
        devices.isNullOrEmpty() -> this.showNotification("There is no device connected", NotificationType.WARNING)
        devices.size > 1 -> {
            val dialog = DeviceSelectionDialog(devices)
            val dialogOk = dialog.showAndGet()
            if (dialogOk && dialog.selectedDevice != null) {
                process(this!!, dialog.selectedDevice!!)
            }
        }
        else -> {
            process(this!!, devices.first())
        }
    }
}

private class DeviceSelectionDialog(private val devices: Array<IDevice>) : DialogWrapper(true) {

    var selectedDevice: IDevice? = null

    init {
        init()
        title = "Select the device"
    }

    override fun createCenterPanel(): JComponent {
        val tableModel = DeviceTableModel(devices)
        val table = JBTable(tableModel).apply {
            rowHeight = 22
            selectionModel.addListSelectionListener { listSelectionEvent ->
                selectedDevice = devices[listSelectionEvent.firstIndex]
                close(OK_EXIT_CODE)
            }
        }

        return JPanel(BorderLayout()).apply {
            layout = BorderLayout()
            add(JScrollPane(table), BorderLayout.CENTER)
            preferredSize = Dimension(400, 200)
        }
    }

}

private class DeviceTableModel(private val devices: Array<IDevice>) : AbstractTableModel() {

    override fun getRowCount(): Int = devices.size

    override fun getColumnCount(): Int = 1

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = devices[rowIndex].name

    override fun getColumnName(column: Int): String = "Device"

}