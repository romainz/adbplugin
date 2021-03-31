package com.canal.android.adb.action

import com.android.ddmlib.IDevice
import com.canal.android.adb.setting.AdbPluginSettingsState
import com.canal.android.adb.util.ShellReceiver
import com.canal.android.adb.util.showNotification
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

abstract class BaseShellAction : BaseDeviceAction() {

    override fun process(project: Project, device: IDevice) {
        device.executeShellCommand(
            getShellCommand(),
            ShellReceiver.getReceiver()
        )
        if (AdbPluginSettingsState.instance.displayAdbNotification) {
            project.showNotification("adb shell ${getShellCommand()}", NotificationType.INFORMATION)
        }
    }

    abstract fun getShellCommand(): String

}