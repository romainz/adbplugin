package com.canal.android.adb.util.dialog

import com.canal.android.adb.setting.AdbPluginSettingsState
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.table.JBTable
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.table.AbstractTableModel

class ApplicationSelectionDialog : DialogWrapper(true) {

    var selectedApplication: String? = null

    init {
        init()
        title = "Select the application"
    }

    override fun createCenterPanel(): JComponent {
        val applications = AdbPluginSettingsState.instance.applications
        val tableModel = ApplicationTableModel(applications)
        val table = JBTable(tableModel).apply {
            rowHeight = 22
            selectionModel.addListSelectionListener { listSelectionEvent ->
                selectedApplication = applications[listSelectionEvent.firstIndex]
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

private class ApplicationTableModel(private val applications: List<String>) : AbstractTableModel() {

    override fun getRowCount(): Int = applications.size

    override fun getColumnCount(): Int = 1

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any = applications[rowIndex]

    override fun getColumnName(column: Int): String = "Device"

}