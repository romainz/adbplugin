package com.canal.android.adb.setting

import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class AdbPluginSettingsComponent {

    val panel: JPanel

    private val displayAdbNotificationCheckbox = JBCheckBox("Display the ADB command in a notification")
    val preferredFocusedComponent: JComponent
        get() = displayAdbNotificationCheckbox

    var displayAdbNotification: Boolean
        get() = displayAdbNotificationCheckbox.isSelected
        set(newStatus) {
            displayAdbNotificationCheckbox.isSelected = newStatus
        }

    init {
        panel = FormBuilder.createFormBuilder()
            //.addLabeledComponent(JBLabel("Enter user name: "), myUserNameText, 1, false)
            .addComponent(displayAdbNotificationCheckbox, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}