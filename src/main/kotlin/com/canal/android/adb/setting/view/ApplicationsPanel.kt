package com.canal.android.adb.setting.view

import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ListUtil
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.*

class ApplicationsPanel(private val controller: Controller) : JPanel(BorderLayout()) {

    private val applicationListComponent: JBList<String> = JBList(DefaultListModel())

    fun getApplications(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until applicationListComponent.itemsCount) {
            list.add(applicationListComponent.model.getElementAt(i))
        }
        return list
    }

    private val listModel: DefaultListModel<String> = applicationListComponent.model as DefaultListModel<String>

    fun addApplication(application: String) {
        listModel.addElement(application)
    }

    fun removeSelected(): String? {
        val selectedValue = applicationListComponent.selectedValue ?: return null
        ListUtil.removeSelectedItems(applicationListComponent)
        return selectedValue
    }

    fun getSelectedItem(): String = applicationListComponent.selectedValue

    init {
        applicationListComponent.apply {
            selectionMode = ListSelectionModel.SINGLE_SELECTION
            cellRenderer = ApplicationRenderer()
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

    private class ApplicationRenderer : DefaultListCellRenderer() {
        override fun getListCellRendererComponent(
            list: JList<*>,
            value: Any,
            index: Int,
            isSelected: Boolean,
            cellHasFocus: Boolean
        ): Component {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            text = " $text"
            return this
        }

        override fun getPreferredSize(): Dimension {
            return JBDimension(0, 20)
        }
    }
}
