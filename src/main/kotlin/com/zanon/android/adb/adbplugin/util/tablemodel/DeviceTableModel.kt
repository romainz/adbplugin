package com.zanon.android.adb.util.tablemodel

import com.zanon.android.adb.setting.model.Device
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
        devices.apply {
            add(device)
            sortBy { it.name?.lowercase() }
        }
        val index = devices.indexOf(device)
        fireTableRowsInserted(index, index)
    }

    fun editDevice(device: Device, index: Int) {
        devices[index] = device
        devices.sortBy { it.name?.lowercase() }
        fireTableRowsUpdated(index, index)
    }

    fun removeDevice(rowIndex: Int) {
        devices.removeAt(rowIndex)
        fireTableRowsDeleted(rowIndex, rowIndex)
    }

    fun getDevice(rowIndex: Int): Device = devices[rowIndex]
}
