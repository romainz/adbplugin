package com.zanon.android.adb.panel

import com.intellij.util.ui.WrapLayout
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.tablemodel.ApplicationTableModel
import com.zanon.android.adb.util.tablemodel.JBTableDoubleClick
import org.jdesktop.swingx.VerticalLayout
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*

object ApplicationsPanel {

    private val textFieldApplication = JTextField()
    private val checkBox = JCheckBox("Restart application")

    fun build(
        sendAdbCommand: (String) -> Unit,
        sendShellCommand: (String) -> Unit,
        showErrorNotification: (String) -> Unit,
    ): JComponent {
        // buttons
        val buttonsPanel = JPanel(WrapLayout(FlowLayout.LEFT)).apply {
//            add(createButton("Install", Action.INSTALL, sendAdbCommand, sendShellCommand, showErrorNotification))
            add(createButton("Start", Action.START, sendAdbCommand, sendShellCommand, showErrorNotification))
            add(createButton("Force Stop", Action.FORCE_STOP, sendAdbCommand, sendShellCommand, showErrorNotification))
            add(createButton("Wipe Data", Action.WIPE_DATE, sendAdbCommand, sendShellCommand, showErrorNotification))
            add(createButton("Uninstall", Action.UNINSTALL, sendAdbCommand, sendShellCommand, showErrorNotification))
        }
        val applications = AdbPluginSettingsState.instance.applications
        val tableModel = ApplicationTableModel(applications.toMutableList())
        val table = JBTableDoubleClick(tableModel).apply {
            rowHeight = 22
            addRowListener(
                simpleClick = { rowIndex ->
                    textFieldApplication.text = applications[rowIndex].id
                },
                doubleClick = { _ ->
                    // Nothing to do
                }
            )
        }
        checkBox.apply {
            text = "Restart application"
            isSelected = true
        }
        val textFieldPanel = JPanel(BorderLayout(5, 0)).apply {
            add(JLabel("Application id: "), BorderLayout.WEST)
            add(textFieldApplication, BorderLayout.CENTER)
        }
        return JPanel().apply {
            layout = VerticalLayout(5)
            add(textFieldPanel)
            add(buttonsPanel)
            add(checkBox)
            add(JScrollPane(table))
        }
    }

    private enum class Action {
        /*INSTALL,*/ START, FORCE_STOP, WIPE_DATE, UNINSTALL
    }

    private fun createButton(
        text: String,
        action: Action,
        sendAdbCommand: (String) -> Unit,
        sendShellCommand: (String) -> Unit,
        showErrorNotification: (String) -> Unit,
    ): JButton =
        JButton(text).apply {
            addActionListener { sendCommand(action, sendAdbCommand, sendShellCommand, showErrorNotification) }
        }

    private fun sendCommand(
        action: Action,
        sendAdbCommand: (String) -> Unit,
        sendShellCommand: (String) -> Unit,
        showErrorNotification: (String) -> Unit
    ) {
        if (textFieldApplication.text.isEmpty()) {
            showErrorNotification("Please, select or type a package name before")
            return
        }
        val application = textFieldApplication.text
        val restartApplication = checkBox.isSelected
        when (action) {
//            Action.INSTALL -> {
//                TODO()
//            }

            Action.START -> {
                sendShellCommand("monkey -p $application -c android.intent.category.LAUNCHER 1")
            }

            Action.FORCE_STOP -> {
                if (restartApplication) {
                    sendShellCommand("am force-stop $application && monkey -p $application -c android.intent.category.LAUNCHER 1")
                } else {
                    sendShellCommand("am force-stop $application")
                }
            }

            Action.WIPE_DATE -> {
                if (restartApplication) {
                    sendShellCommand("pm clear $application && monkey -p $application -c android.intent.category.LAUNCHER 1")
                } else {
                    sendShellCommand("pm clear $application")
                }
            }

            Action.UNINSTALL -> {
                sendAdbCommand("uninstall $application")
            }
        }
    }
}
