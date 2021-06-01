package com.zanon.android.adb.setting.view

import com.zanon.android.adb.setting.model.Application
import com.zanon.android.adb.util.tablemodel.ApplicationTableModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class ApplicationsPanel(private val controller: Controller) : JPanel(BorderLayout()) {

    private val tableModel: ApplicationTableModel = ApplicationTableModel()
    private val applicationListComponent: JBTable = JBTable(tableModel)

    fun getApplications(): List<Application> {
        val list = mutableListOf<Application>()
        for (row in 0 until applicationListComponent.rowCount) {
            val name = applicationListComponent.model.getValueAt(row, 0).toString()
            val id = applicationListComponent.model.getValueAt(row, 1).toString()
            list.add(Application(name, id))
        }
        return list
    }

    fun addApplication(application: Application) {
        tableModel.addApplication(application)
    }

    fun removeSelected() {
        val selectedRowIndex = applicationListComponent.selectedRow
        tableModel.removeApplication(selectedRowIndex)
    }

    fun getSelectedItem(): Application = tableModel.getApplication(applicationListComponent.selectedRow)

    init {
        applicationListComponent.apply {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
            emptyText.text = "No application defined"
        }
        add(
            ToolbarDecorator.createDecorator(applicationListComponent)
                .setAddAction { controller.addApplication() }
//                    .setEditAction { myController.editApplication() }
                .setRemoveAction { controller.removeApplication() }
                .disableUpDownActions().createPanel(), BorderLayout.CENTER
        )
        border = IdeBorderFactory.createTitledBorder(
            "Applications",
            false,
            JBUI.insetsTop(8)
        ).setShowLine(false)
    }

    interface Controller {

        fun editApplication()

        fun addApplication()

        fun removeApplication()
    }
}
