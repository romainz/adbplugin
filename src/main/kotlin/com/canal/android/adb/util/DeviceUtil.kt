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

fun Project.toCurrentDevice(): IDevice? {
    val devices = this.let { AndroidSdkUtils.getDebugBridge(it)?.devices }
    return when {
        devices.isNullOrEmpty() -> {
            this.showNotification("There is no device connected", NotificationType.WARNING)
            null
        }
        devices.size > 1 -> {
            val dialog = DeviceSelectionDialog(devices)
            val dialogOk = dialog.showAndGet()
            if (dialogOk && dialog.selectedDevice != null) {
                dialog.selectedDevice!!
            } else {
                null
            }
        }
        else -> {
            devices.first()
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

        return JPanel().apply {
            layout = BorderLayout()
            add(JScrollPane(table), BorderLayout.CENTER)
            preferredSize = PREFERRED_SIZE
            setResizable(false)
        }
    }

    private companion object {
        val PREFERRED_SIZE = Dimension(600, 200)
    }

}

private class DeviceTableModel(private val devices: Array<IDevice>) : AbstractTableModel() {

    override fun getRowCount(): Int = devices.size

    override fun getColumnCount(): Int = COLUMN_TITLE.size

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val device = devices[rowIndex]
        return when (columnIndex) {
            0 -> {
                // name
                val separatorIndex = device.name.lastIndexOf(NAME_SEPARATOR)
                if (separatorIndex > 0) {
                    device.name.subSequence(0, separatorIndex)
                } else {
                    device.name
                }
            }
            1 -> {
                // ip
                device.serialNumber
            }
            else -> {
                // status
                device.state.name.toLowerCase()
            }
        }
    }

    override fun getColumnName(column: Int): String = COLUMN_TITLE[column]

    private companion object {
        val COLUMN_TITLE = arrayOf("Name", "serial number", "status")
        const val NAME_SEPARATOR = "-"
    }
}