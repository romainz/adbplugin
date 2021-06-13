package com.zanon.android.adb.setting.view

import com.android.tools.idea.uibuilder.handlers.constraint.draw.DrawConnection.GAP
import com.zanon.android.adb.setting.model.Device
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class EditDeviceDialog(device: Device?) {
    val nameTextField: JTextField = JTextField(device?.name)
    val ipTextField: JTextField = JTextField(device?.ip)
    val mainPanel: JPanel = JPanel(BorderLayout())

    init {
        mainPanel.apply {
            layout = GridLayout(0, 2, GAP, GAP)
            add(JLabel("Name: "))
            add(nameTextField)
            add(JLabel("Ip address: "))
            add(ipTextField)
        }
    }
}