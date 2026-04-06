package com.zanon.android.adb.setting.view

import com.zanon.android.adb.setting.model.InputText
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField


class EditInputTextDialog(inputText: InputText?) {
    val nameTextField: JTextField = JTextField(inputText?.name)
    val textTextField: JTextField = JTextField(inputText?.text)
    val mainPanel: JPanel = JPanel(BorderLayout())

    init {
        mainPanel.apply {
            layout = GridLayout(0, 2)
            add(JLabel("Name: "))
            add(nameTextField)
            add(JLabel("Text: "))
            add(textTextField)
        }
    }
}