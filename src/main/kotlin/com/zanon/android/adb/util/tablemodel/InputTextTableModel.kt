package com.zanon.android.adb.util.tablemodel

import com.zanon.android.adb.setting.model.InputText
import javax.swing.table.AbstractTableModel

class InputTextTableModel(
    private val inputTexts: MutableList<InputText> = mutableListOf()
) : AbstractTableModel() {

    override fun getRowCount(): Int = inputTexts.size

    override fun getColumnCount(): Int = COLUMN_TITLE.size

    override fun getColumnName(column: Int): String = COLUMN_TITLE[column]

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val inputText = inputTexts[rowIndex]
        return when (columnIndex) {
            0 -> inputText.name ?: ""
            else -> inputText.text ?: ""
        }
    }

    private companion object {
        val COLUMN_TITLE = arrayOf("Name", "Text")
    }

    fun add(inputText: InputText) {
        inputTexts.apply {
            add(inputText)
            sortBy { it.name?.lowercase() }
        }
        val index = inputTexts.indexOf(inputText)
        fireTableRowsInserted(index, index)
    }

    fun edit(inputText: InputText, index: Int) {
        inputTexts[index] = inputText
        inputTexts.sortBy { it.name?.lowercase() }
        fireTableRowsUpdated(index, index)
    }

    fun remove(rowIndex: Int) {
        inputTexts.removeAt(rowIndex)
        fireTableRowsDeleted(rowIndex, rowIndex)
    }

    fun get(rowIndex: Int): InputText = inputTexts[rowIndex]
}
