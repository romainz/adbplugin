package com.zanon.android.adb.util.tablemodel

import com.zanon.android.adb.setting.model.Deeplink
import javax.swing.table.AbstractTableModel

class DeeplinkTableModel(
    private val deeplinks: MutableList<Deeplink> = mutableListOf()
) : AbstractTableModel() {

    override fun getRowCount(): Int = deeplinks.size

    override fun getColumnCount(): Int = COLUMN_TITLE.size

    override fun getColumnName(column: Int): String = COLUMN_TITLE[column]

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val deeplink = deeplinks[rowIndex]
        return when (columnIndex) {
            0 -> deeplink.name ?: ""
            else -> deeplink.command ?: ""
        }
    }

    private companion object {
        val COLUMN_TITLE = arrayOf("Name", "Deeplink")
    }

    fun addDeeplink(deeplink: Deeplink) {
        deeplinks.apply {
            add(deeplink)
            sortBy { it.name?.lowercase() }
        }
        val index = deeplinks.indexOf(deeplink)
        fireTableRowsInserted(index, index)
    }

    fun editDeeplink(deeplink: Deeplink, index: Int) {
        deeplinks[index] = deeplink
        deeplinks.sortBy { it.name?.lowercase() }
        fireTableRowsUpdated(index, index)
    }

    fun removeDeeplink(rowIndex: Int) {
        deeplinks.removeAt(rowIndex)
        fireTableRowsDeleted(rowIndex, rowIndex)
    }

    fun getDeeplink(rowIndex: Int): Deeplink = deeplinks[rowIndex]
}
