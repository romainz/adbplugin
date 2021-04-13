package com.canal.android.adb.action

import com.android.ddmlib.IDevice
import com.canal.android.adb.util.toCurrentDevice
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

abstract class BaseDeviceAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val eventProject = event.project

        eventProject?.toCurrentDevice()?.let { device ->
            process(eventProject, device)
        }
    }

    abstract fun process(project: Project, device: IDevice)

}