package com.zanon.android.adb.action

import com.google.common.util.concurrent.ThreadFactoryBuilder
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.zanon.android.adb.setting.AdbPluginSettingsState
import com.zanon.android.adb.util.showNotification
import org.jetbrains.android.sdk.AndroidSdkUtils
import java.io.BufferedReader
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


abstract class BaseAdbAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        EXECUTOR.submit {
            val androidSdkPath = AndroidSdkUtils.findAdb(event.project).adbPath?.toString()
            if (androidSdkPath.isNullOrBlank()) {
                event.project.showNotification("Android SDK path not found", NotificationType.ERROR)
            } else {
                event.project?.let { project ->
                    process(project, androidSdkPath)
                }
            }
        }
    }

    open fun process(project: Project, adbPath: String) {
        val adbCommand = " ${getAdbCommand()}"
        if (AdbPluginSettingsState.instance.displayAdbNotification) {
            project.showNotification(adbCommand, NotificationType.INFORMATION, "commandId")
        }
        val process = Runtime.getRuntime().exec(adbPath + adbCommand)
        // logs
        var message = process.inputStream.bufferedReader().use(BufferedReader::readText)
        if (message.isNotEmpty()) {
            project.showNotification(message, NotificationType.INFORMATION)
        }
        // errors
        message = process.errorStream.bufferedReader().use(BufferedReader::readText)
        if (message.isNotEmpty()) {
            project.showNotification(message, NotificationType.ERROR)
        }
    }

    abstract fun getAdbCommand(): String

    protected companion object {

        val EXECUTOR: ExecutorService =
            Executors.newCachedThreadPool(ThreadFactoryBuilder().setNameFormat("AdbWifi-%d").build())
    }
}