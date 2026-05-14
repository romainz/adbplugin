package com.zanon.android.adb.util

import com.zanon.android.adb.android.Settings

object ShellUtil {

    const val DEEPLINK_ALL_APPS = "am start -a android.intent.action.ALL_APPS"
    const val DEEPLINK_SETTINGS = "am start -a ${Settings.ACTION_SETTINGS}"
    const val DEEPLINK_DEVELOPMENT_SETTINGS = "am start -a ${Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS}"
    const val DEEPLINK_WIFI_SETTINGS = "am start -a ${Settings.ACTION_WIFI_SETTINGS}"
    const val DEEPLINK_DEVICE_INFO_SETTINGS = "am start -a ${Settings.ACTION_WIFI_SETTINGS}"
    const val DEEPLINK_LANGUAGE_SETTINGS = "am start -a ${Settings.ACTION_LOCALE_SETTINGS}"

    const val LAYOUT_BOUNDS_ENABLE = "setprop debug.layout true ; service call activity 1599295570"
    const val LAYOUT_BOUNDS_DISABLE = "setprop debug.layout false ; service call activity 1599295570"

    const val MOBILE_DATA_ENABLE = "svc data enable"
    const val MOBILE_DATA_DISABLE = "svc data disable"

    const val OFFLINE_ENABLE = "svc wifi disable && svc data disable"
    const val OFFLINE_DISABLE = "svc wifi enable && svc data enable"

    const val TALKBACK_ENABLE =
        "settings put secure enabled_accessibility_services com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService"
    const val TALKBACK_DISABLE =
        "settings put secure enabled_accessibility_services com.android.talkback/com.google.android.marvin.talkback.TalkBackService"

    const val WIFI_ENABLE = "svc wifi enable"
    const val WIFI_DISABLE = "svc wifi disable"

}