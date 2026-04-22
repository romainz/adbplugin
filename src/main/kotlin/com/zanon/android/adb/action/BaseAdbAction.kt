package com.zanon.android.adb.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.zanon.android.adb.util.AdbCommandDelegate


abstract class BaseAdbAction : AnAction() {

    private val adbCommandDelegate: AdbCommandDelegate by lazy { AdbCommandDelegate() }

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            adbCommandDelegate.sendAdbCommand(getAdbCommand(), project)
        }
    }

    abstract fun getAdbCommand(): String

}