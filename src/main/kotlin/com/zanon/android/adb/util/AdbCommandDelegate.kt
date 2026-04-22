package com.zanon.android.adb.util

import com.google.common.util.concurrent.ThreadFactoryBuilder
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.zanon.android.adb.setting.AdbPluginSettingsState
import org.jetbrains.android.sdk.AndroidSdkUtils
import java.io.BufferedReader
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AdbCommandDelegate {

    fun sendAdbCommand(command: String, project: Project) {
        EXECUTOR.submit {
            val adbPath = AndroidSdkUtils.findAdb(project).adbPath?.toString()
            if (adbPath.isNullOrBlank()) {
                project.showNotification("Android SDK path not found", NotificationType.ERROR)
            } else {
                process(command, project, adbPath)
            }
        }
    }

    private fun process(command: String, project: Project, adbPath: String) {
        val adbCommand = " $command"
        if (AdbPluginSettingsState.instance.displayAdbNotification) {
            project.showNotification(adbCommand, NotificationType.INFORMATION, "commandId")
        }
        val process = Runtime.getRuntime().exec(adbPath + adbCommand)
        // logs
        var message = process.inputStream.bufferedReader().use(BufferedReader::readText)
        if (AdbPluginSettingsState.instance.displayAdbNotification && message.isNotEmpty()) {
            project.showNotification(message, NotificationType.INFORMATION)
        }
        // errors
        message = process.errorStream.bufferedReader().use(BufferedReader::readText)
        if (AdbPluginSettingsState.instance.displayAdbNotification && message.isNotEmpty()) {
            project.showNotification(message, NotificationType.ERROR)
        }
    }

    private companion object {

        val EXECUTOR: ExecutorService =
            Executors.newCachedThreadPool(ThreadFactoryBuilder().setNameFormat("AdbWifi-%d").build())
    }
}