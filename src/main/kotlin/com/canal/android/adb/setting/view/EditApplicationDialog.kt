package com.canal.android.adb.setting.view

import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JTextField


class EditApplicationDialog(applicationId: String?) {
    val applicationTextField: JTextField = JTextField(applicationId)
    val mainPanel: JPanel = JPanel(BorderLayout())

    init {
        mainPanel.add(applicationTextField)
    }
}