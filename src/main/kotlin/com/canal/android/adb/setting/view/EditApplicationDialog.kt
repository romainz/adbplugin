package com.canal.android.adb.setting.view

import com.android.tools.idea.uibuilder.handlers.constraint.draw.DrawConnection
import com.canal.android.adb.setting.model.Application
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class EditApplicationDialog(application: Application?) {
    val nameTextField: JTextField = JTextField(application?.name)
    val idTextField: JTextField = JTextField(application?.id)
    val mainPanel: JPanel = JPanel(BorderLayout())

    init {
        mainPanel.apply {
            layout = GridLayout(0, 2, DrawConnection.GAP, DrawConnection.GAP)
            add(JLabel("Name: "))
            add(nameTextField)
            add(JLabel("Package name: "))
            add(idTextField)
            preferredSize = Dimension(400, 200)
        }
    }
}