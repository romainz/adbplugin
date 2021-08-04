package com.zanon.android.adb.setting.view

import com.android.tools.idea.uibuilder.handlers.constraint.draw.DrawConnection.GAP
import com.zanon.android.adb.setting.model.Deeplink
import com.zanon.android.adb.setting.model.Device
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class EditDeeplinkDialog(deeplink: Deeplink?) {
    val nameTextField: JTextField = JTextField(deeplink?.name)
    val commandTextField: JTextField = JTextField(deeplink?.command)
    val mainPanel: JPanel = JPanel(BorderLayout())

    init {
        mainPanel.apply {
            layout = GridLayout(0, 2, GAP, GAP)
            add(JLabel("Name: "))
            add(nameTextField)
            add(JLabel("Command: "))
            add(commandTextField)
        }
    }
}