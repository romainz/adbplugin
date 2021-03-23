package com.canal.android.adb.util

import com.android.ddmlib.IDevice
import com.intellij.openapi.project.Project
import org.jetbrains.android.sdk.AndroidSdkUtils

fun Project?.toCurrentDevice(process: (IDevice) -> Unit) {
    val devices = this?.let { AndroidSdkUtils.getDebugBridge(it)?.devices }
    when {
        devices.isNullOrEmpty() -> this.showNotification("There is no device connected")
        devices.size > 1 -> this.showNotification("There is more than one device connected")
        else -> {
            process(devices.first())
        }
    }
}