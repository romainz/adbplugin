package com.zanon.android.adb.panel

import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.ui.components.panels.VerticalLayout
import javax.swing.*

object ApplicationsActionsPanel {

    private val restartCheckBox = JCheckBox("Restart application").apply {
        isSelected = true
    }

    fun build(
        sendAdbCommand: (String) -> Unit,
        sendShellCommand: (String) -> Unit,
        showErrorNotification: (String) -> Unit,
        getSelectedApplicationId: () -> String
    ): JComponent {

        fun createButton(
            text: String,
            action: Action,
        ): JButton = JButton(text).apply {
            addActionListener {
                sendCommand(
                    action,
                    sendAdbCommand,
                    sendShellCommand,
                    showErrorNotification,
                    getSelectedApplicationId
                )
            }
        }

        return JPanel(VerticalLayout(5)).apply {
            add(createButton("Install", Action.INSTALL))
            add(createButton("Start", Action.START))
            add(createButton("Uninstall", Action.UNINSTALL))
            add(JSeparator())
            add(restartCheckBox)
            add(createButton("Force Stop", Action.FORCE_STOP))
            add(createButton("Wipe Data", Action.WIPE_DATE))
        }
    }

    private enum class Action {
        INSTALL, START, FORCE_STOP, WIPE_DATE, UNINSTALL
    }

    private fun sendCommand(
        action: Action,
        sendAdbCommand: (String) -> Unit,
        sendShellCommand: (String) -> Unit,
        showErrorNotification: (String) -> Unit,
        getSelectedApplicationId: () -> String,
    ) {
        val application = getSelectedApplicationId()
        if (application.isEmpty()) {
            showErrorNotification("Please select an Application ID before")
            return
        }
        val restartApplication = restartCheckBox.isSelected
        when (action) {
            Action.INSTALL -> {
                installApk(sendAdbCommand)
            }

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

    private fun installApk(
        sendAdbCommand: (String) -> Unit
    ) {

        val apkFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileDescriptor(), null, null)
        apkFile?.let { file ->
            sendAdbCommand("install -r -t -d ${file.path}")
        }
    }
}