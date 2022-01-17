package com.zanon.android.adb.util.dialog

import com.intellij.CommonBundle
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JButton
import javax.swing.JPanel

abstract class BaseCloseDialogWrapper : DialogWrapper(true)  {

    override fun createButtonsPanel(buttons: MutableList<out JButton>): JPanel {
        getButton(okAction)?.apply {
            text = CommonBundle.getCloseButtonText()
        }
        getButton(cancelAction)?.apply {
            isVisible = false
        }
        return super.createButtonsPanel(buttons)
    }
}