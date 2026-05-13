package com.zanon.android.adb.util

object ShellUtil {

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