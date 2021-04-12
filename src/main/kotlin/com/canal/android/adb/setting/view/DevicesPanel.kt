package com.canal.android.adb.setting.view

import com.canal.android.adb.setting.model.Device
import com.canal.android.adb.util.tablemodel.DeviceTableModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel
import javax.swing.table.AbstractTableModel


class DevicesPanel(private val controller: Controller) : JPanel(BorderLayout()) {

    private val tableModel: DeviceTableModel = DeviceTableModel()
    private val deviceTableComponent: JBTable = JBTable(tableModel)

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
//                    .setEditAction { myController.editDevice() }
                .setRemoveAction { controller.removeDevice() }
                .disableUpDownActions().createPanel(), BorderLayout.CENTER
        )
        border = IdeBorderFactory.createTitledBorder(
            "Devices",
            false,
            JBUI.insetsTop(8)
        ).setShowLine(false)
    }

    interface Controller {

        fun editDevice()

        fun addDevice()

        fun removeDevice()
    }


}
