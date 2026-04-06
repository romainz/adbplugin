package com.zanon.android.adb.util.tablemodel

import com.zanon.android.adb.setting.model.Application
import javax.swing.table.AbstractTableModel

class ApplicationTableModel(
    private val applications: MutableList<Application> = mutableListOf()
) : AbstractTableModel() {

    override fun getRowCount(): Int = applications.size

    override fun getColumnCount(): Int = COLUMN_TITLE.size

    override fun getColumnName(column: Int): String = COLUMN_TITLE[column]

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val application = applications[rowIndex]
        return when (columnIndex) {
            0 -> application.name
            else -> application.id
        }
    }

    fun editApplication(application: Application, index: Int) {
        applications[index] = application
        applications.sortBy { it.name.lowercase() }
        fireTableRowsUpdated(index, index)
    }

    fun addApplication(application: Application) {
        applications.apply {
            add(application)
            sortBy { it.name.lowercase() }
        }
        val index = applications.indexOf(application)
        fireTableRowsInserted(index, index)
    }

    fun removeApplication(rowIndex: Int) {
        applications.removeAt(rowIndex)
        fireTableRowsDeleted(rowIndex, rowIndex)
    }

    fun getApplication(rowIndex: Int): Application = applications[rowIndex]

    private companion object {
        val COLUMN_TITLE = arrayOf("Name", "Application Id")
    }
}
