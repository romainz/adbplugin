package com.zanon.android.adb.setting.view

import com.zanon.android.adb.setting.model.Deeplink
import java.awt.BorderLayout
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
            layout = GridLayout(0, 2)
            add(JLabel("Name: "))
            add(nameTextField)
            add(JLabel("Command: "))
            add(commandTextField)
        }
    }
}