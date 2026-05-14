package com.zanon.android.adb.panel

import com.zanon.android.adb.util.ShellUtil
import com.zanon.android.adb.util.ShellUtil.DEEPLINK_ALL_APPS
import com.zanon.android.adb.util.ShellUtil.DEEPLINK_DEVELOPMENT_SETTINGS
import com.zanon.android.adb.util.ShellUtil.DEEPLINK_DEVICE_INFO_SETTINGS
import com.zanon.android.adb.util.ShellUtil.DEEPLINK_LANGUAGE_SETTINGS
import com.zanon.android.adb.util.ShellUtil.DEEPLINK_SETTINGS
import com.zanon.android.adb.util.ShellUtil.DEEPLINK_WIFI_SETTINGS
import org.jdesktop.swingx.VerticalLayout
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*

object ShortcutsPanel {

    fun build(
        sendShellCommand: (String) -> Unit
    ): JPanel {

        fun createButtonsRow(title: String, shellCommandEnable: String, shellCommandDisable: String): JPanel =
            JPanel(BorderLayout()).apply {
                add(JLabel(title), BorderLayout.WEST)
                add(
                    JPanel(FlowLayout(FlowLayout.RIGHT)).apply {
                        add(createButton("Enable", shellCommandEnable, sendShellCommand))
                        add(createButton("Disable", shellCommandDisable, sendShellCommand))
                    },
                    BorderLayout.EAST
                )
            }

        return JPanel().apply {
            layout = VerticalLayout(5)

            add(createButtonsRow("Offline", ShellUtil.OFFLINE_ENABLE, ShellUtil.OFFLINE_DISABLE))
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(createButtonsRow("Wifi", ShellUtil.WIFI_ENABLE, ShellUtil.WIFI_DISABLE))
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(createButtonsRow("Mobile Data", ShellUtil.MOBILE_DATA_ENABLE, ShellUtil.MOBILE_DATA_DISABLE))
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(createButtonsRow("Talkback", ShellUtil.TALKBACK_ENABLE, ShellUtil.TALKBACK_DISABLE))
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(createButtonsRow("Layout bounds", ShellUtil.LAYOUT_BOUNDS_ENABLE, ShellUtil.LAYOUT_BOUNDS_DISABLE))

            add(JSeparator(SwingConstants.HORIZONTAL))

            add(createButton("Settings", DEEPLINK_SETTINGS, sendShellCommand))
            add(createButton("Development Settings", DEEPLINK_DEVELOPMENT_SETTINGS, sendShellCommand))
            add(createButton("Wifi Settings", DEEPLINK_WIFI_SETTINGS, sendShellCommand))
            add(createButton("Device Information", DEEPLINK_DEVICE_INFO_SETTINGS, sendShellCommand))
            add(createButton("Language", DEEPLINK_LANGUAGE_SETTINGS, sendShellCommand))
            add(createButton("All Apps", DEEPLINK_ALL_APPS, sendShellCommand))
        }
    }

    private fun createButton(
        text: String,
        shellCommand: String,
        sendShellCommand: (String) -> Unit,
    ): JButton =
        JButton(text).apply {
            addActionListener { sendShellCommand(shellCommand) }
        }


}