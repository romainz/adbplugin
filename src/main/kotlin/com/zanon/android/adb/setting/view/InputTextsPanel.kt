package com.zanon.android.adb.setting.view

import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.zanon.android.adb.setting.model.InputText
import com.zanon.android.adb.util.tablemodel.InputTextTableModel
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel


class InputTextsPanel(private val controller: Controller) : JPanel(BorderLayout()) {

    private val tableModel: InputTextTableModel = InputTextTableModel()
    private val tableComponent: JBTable = JBTable(tableModel)

    fun getInputTexts(): List<InputText> {
        val list = mutableListOf<InputText>()
        for (row in 0 until tableComponent.rowCount) {
            val name = tableComponent.model.getValueAt(row, 0).toString()
            val ip = tableComponent.model.getValueAt(row, 1).toString()
            list.add(InputText(name, ip))
        }
        return list
    }

    fun add(inputText: InputText) {
        tableModel.add(inputText)
    }

    fun edit(inputText: InputText) {
        val selectedRowIndex = tableComponent.selectedRow
        tableModel.edit(inputText, selectedRowIndex)
    }

    fun removeSelected() {
        val selectedRowIndex = tableComponent.selectedRow
        tableModel.remove(selectedRowIndex)
    }

    fun getSelectedItem(): InputText = tableModel.get(tableComponent.selectedRow)

    init {
        tableComponent.apply {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
            emptyText.text = "No device added"
        }
        add(
            ToolbarDecorator.createDecorator(tableComponent)
                .setAddAction { controller.addInputText() }
                .setEditAction { controller.editInputText() }
                .setRemoveAction { controller.removeInputText() }
                .disableUpDownActions().createPanel(), BorderLayout.CENTER
        )
    }

    interface Controller {

        fun editInputText()

        fun addInputText()

        fun removeInputText()
    }


}
