package com.zanon.android.adb.action.device

import com.android.ddmlib.IDevice
import com.zanon.android.adb.action.BaseShellAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogBuilder
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField

class InputTextAction : BaseShellAction() {

    private var inputText: String = ""

    override fun process(project: Project, device: IDevice) {
        val textField = JTextField()
        val button = JButton("Send").apply {
            addActionListener {
                if (textField.text.isNotEmpty()) {
                    inputText = textField.text
                    super.process(project, device)
                }
            }
        }
        val panel = JPanel(BorderLayout()).apply {
            add(textField, BorderLayout.NORTH)
            add(button, BorderLayout.SOUTH)
        }
        DialogBuilder(project).apply {
            setPreferredFocusComponent(textField)
            setCenterPanel(panel)
            setTitle("Input Text")
            okActionEnabled(false)
            showModal(true)
        }
    }

    override fun getShellCommand(): String = "input text $inputText"

}

