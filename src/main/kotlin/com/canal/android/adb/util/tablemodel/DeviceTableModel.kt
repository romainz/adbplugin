package com.canal.android.adb.util.tablemodel

import com.canal.android.adb.setting.model.Device
import javax.swing.table.AbstractTableModel

class DeviceTableModel(
    private val devices: MutableList<Device> = mutableListOf()
) : AbstractTableModel() {

    override fun getRowCount(): Int = devices.size

    override fun getColumnCount(): Int = COLUMN_TITLE.size

    override fun getColumnName(column: Int): String = COLUMN_TITLE[column]

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val device = devices[rowIndex]
        return when (columnIndex) {
            0 -> device.name ?: ""
            else -> device.ip ?: ""
        }
    }

    private companion object {
        val COLUMN_TITLE = arrayOf("Name", "Ip address")
    }

    fun addDevice(device: Device) {
        devices.add(device)
        fireTableRowsInserted(devices.size - 1, devices.size - 1)
    }

    fun removeDevice(rowIndex: Int) {
        devices.removeAt(rowIndex)
        fireTableRowsDeleted(rowIndex, rowIndex)
    }

    fun getDevice(rowIndex: Int): Device = devices[rowIndex]
}