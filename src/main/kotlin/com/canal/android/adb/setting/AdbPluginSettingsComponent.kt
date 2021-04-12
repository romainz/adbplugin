package com.canal.android.adb.setting

import com.canal.android.adb.setting.view.EditApplicationDialog
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ListUtil
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBList
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.*


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 *
 * took inspiration of FileTypeConfigurable
 */
class AdbPluginSettingsComponent {

    val panel: JPanel
    private val applicationsPanel = ApplicationsPanel(this)

    private val displayAdbNotificationCheckbox = JBCheckBox("Display the ADB command in a notification")

    val preferredFocusedComponent: JComponent
        get() = displayAdbNotificationCheckbox

    var displayAdbNotification: Boolean
        get() = displayAdbNotificationCheckbox.isSelected
        set(newStatus) {
            displayAdbNotificationCheckbox.isSelected = newStatus
        }

    var applications: List<String>
        get() = applicationsPanel.getApplications()
        set(newApplications) {
            for (application in newApplications) {
                applicationsPanel.addApplication(application)
            }
        }

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(displayAdbNotificationCheckbox, 1)
            .addComponent(applicationsPanel, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun editApplication() {
        val item: String = applicationsPanel.selectedItem
        editApplication(item)
    }

    private fun addApplication() {
        editApplication(null)
    }

    private fun editApplication(application: String?) {
        val title = "application id"
        val dialog = EditApplicationDialog(application)
        val builder = DialogBuilder(applicationsPanel)
        builder.setPreferredFocusComponent(dialog.applicationTextField)
        builder.setCenterPanel(dialog.mainPanel)
        builder.setTitle(title)
        builder.showModal(true)
        if (builder.dialogWrapper.isOK) {
            val newApplication = dialog.applicationTextField.text ?: return
            if (application.isNullOrEmpty()) {
                // add
                applicationsPanel.addApplication(newApplication)
            } else {
                // edit - todo
            }
        }
    }

    private fun removeApplication() {
        applicationsPanel.removeSelected()
    }

    class ApplicationsPanel(private val myController: AdbPluginSettingsComponent) : JPanel(BorderLayout()) {
        private val applicationListComponent: JBList<String> = JBList(DefaultListModel())

        fun getApplications(): List<String> {
            val list = mutableListOf<String>()
            for (i in 0 until applicationListComponent.itemsCount) {
                list.add(applicationListComponent.model.getElementAt(i))
            }
            return list
        }

        private val listModel: DefaultListModel<String>
            private get() = applicationListComponent.model as DefaultListModel<String>

        fun addApplication(application: String) {
            listModel.addElement(application)
        }

        fun removeSelected(): String? {
            val selectedValue = applicationListComponent.selectedValue ?: return null
            ListUtil.removeSelectedItems(applicationListComponent)
            return selectedValue
        }

        val selectedItem: String
            get() = applicationListComponent.selectedValue

        init {
            applicationListComponent.apply {
                selectionMode = ListSelectionModel.SINGLE_SELECTION
                setCellRenderer(ApplicationRenderer())
                emptyText.text = "No application defined"
            }
            add(
                ToolbarDecorator.createDecorator(applicationListComponent)
                    .setAddAction { myController.addApplication() }
//                    .setEditAction { myController.editApplication() }
                    .setRemoveAction { myController.removeApplication() }
                    .disableUpDownActions().createPanel(), BorderLayout.CENTER
            )
            border = IdeBorderFactory.createTitledBorder(
                "Applications",
                false,
                JBUI.insetsTop(8)
            ).setShowLine(false)
        }
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
