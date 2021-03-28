package com.canal.android.adb.util

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

fun Project?.showNotification(message: String, type: NotificationType) {
    NotificationGroup("canal", NotificationDisplayType.BALLOON)
        .createNotification(
            "ADB+ Plugin",
            message,
            type,
            null
        ).notify(this)
}