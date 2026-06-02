package com.zanon.android.adb.panel

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.zanon.android.adb.setting.AdbPluginSettingsConfigurable

class AdbToolsToolWindowFactory : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val adbToolsPanel = AdbToolsPanel(project)
        val content = ContentFactory.getInstance()
            .createContent(adbToolsPanel, "", false)

        toolWindow.apply {
            contentManager.addContent(content)
            setTitleActions(listOf(OpenSettingsAction(project)))
        }
    }

    private class OpenSettingsAction(
        private val project: Project
    ) : AnAction("Settings", "Open ADB Tools settings", AllIcons.General.Settings) {

        override fun actionPerformed(event: AnActionEvent) {
            ShowSettingsUtil.getInstance().showSettingsDialog(
                project,
                AdbPluginSettingsConfigurable::class.java
            )
        }
    }
}
