package com.zanon.android.adb.setting.view

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import com.zanon.android.adb.setting.model.Deeplink
import com.zanon.android.adb.util.tablemodel.DeeplinkTableModel
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel


class DeeplinksPanel(private val controller: Controller) : JPanel(BorderLayout()) {

    private val tableModel: DeeplinkTableModel = DeeplinkTableModel()
    private val deeplinkTableComponent: JBTable = JBTable(tableModel)

    fun getDeeplinks(): List<Deeplink> {
        val list = mutableListOf<Deeplink>()
        for (row in 0 until deeplinkTableComponent.rowCount) {
            val name = deeplinkTableComponent.model.getValueAt(row, 0).toString()
            val ip = deeplinkTableComponent.model.getValueAt(row, 1).toString()
            list.add(Deeplink(name, ip))
        }
        return list
    }

    fun addDeeplink(deeplink: Deeplink) {
        tableModel.addDeeplink(deeplink)
    }

    fun editDeeplink(deeplink: Deeplink) {
        val selectedRowIndex = deeplinkTableComponent.selectedRow
        tableModel.editDeeplink(deeplink, selectedRowIndex)
    }

    fun removeSelected() {
        val selectedRowIndex = deeplinkTableComponent.selectedRow
        tableModel.removeDeeplink(selectedRowIndex)
    }

    fun getSelectedItem(): Deeplink = tableModel.getDeeplink(deeplinkTableComponent.selectedRow)

    init {
        deeplinkTableComponent.apply {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
            emptyText.text = "No deeplink added"
        }
        add(
            ToolbarDecorator.createDecorator(deeplinkTableComponent)
                .setAddAction { controller.addDeeplink() }
                .setEditAction { controller.editDeeplink() }
                .setRemoveAction { controller.removeDeeplink() }
                .disableUpDownActions().createPanel(), BorderLayout.CENTER
        )
        border = IdeBorderFactory.createTitledBorder(
            "Deeplinks",
            false,
            JBUI.insetsTop(8)
        ).setShowLine(false)
    }

    interface Controller {

        fun editDeeplink()

        fun addDeeplink()

        fun removeDeeplink()
    }


}
