package com.zanon.android.adb.util

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

fun Project?.showNotification(message: String, type: NotificationType, notificationId: String = "default_id") {
    NotificationGroup(notificationId, NotificationDisplayType.BALLOON)
        .createNotification(
            "ADB Tools Plugin",
            message,
            type,
            null
        ).notify(this)
}