package com.zanon.android.adb.setting.view

import com.intellij.ui.ToolbarDecorator
import com.zanon.android.adb.setting.model.Device
import com.zanon.android.adb.util.tablemodel.DeviceTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel


class DevicesPanel(
    private val controller: Controller,
    private val doubleClick: () -> Unit
) : JPanel(BorderLayout()) {

    private val tableModel: DeviceTableModel = DeviceTableModel()
    private val deviceTableComponent: JBTableDoubleClick = JBTableDoubleClick(tableModel)
        .apply {
            addRowListener(doubleClick = { doubleClick() })
        }

    fun getDevices(): List<Device> {
        val list = mutableListOf<Device>()
        for (row in 0 until deviceTableComponent.rowCount) {
            val name = deviceTableComponent.model.getValueAt(row, 0).toString()
            val ip = deviceTableComponent.model.getValueAt(row, 1).toString()
            list.add(Device(name, ip))
        }
        return list
    }

    fun addDevice(device: Device) {
        tableModel.addDevice(device)
    }

    fun editDevice(device: Device) {
        val selectedRowIndex = deviceTableComponent.selectedRow
        tableModel.editDevice(device, selectedRowIndex)
    }

    fun removeSelected() {
        val selectedRowIndex = deviceTableComponent.selectedRow
        tableModel.removeDevice(selectedRowIndex)
    }

    fun getSelectedItem(): Device = tableModel.getDevice(deviceTableComponent.selectedRow)

    init {
        deviceTableComponent.apply {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
            emptyText.text = "No device added"
        }
        add(
            ToolbarDecorator.createDecorator(deviceTableComponent)
                .setAddAction { controller.addDevice() }
                .setEditAction { controller.editDevice() }
                .setRemoveAction { controller.removeDevice() }
                .disableUpDownActions().createPanel(), BorderLayout.CENTER
        )
    }

    interface Controller {

        fun editDevice()

        fun addDevice()

        fun removeDevice()
    }


}
