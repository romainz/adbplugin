package com.zanon.android.adb.util

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object Icons {

    val DISCONNECT: Icon
        get() = IconLoader.getIcon("/icons/disconnect.svg", javaClass)
    val FAST_FORWARD: Icon
        get() = IconLoader.getIcon("/icons/fast_forward.svg", javaClass)
    val PAUSE: Icon
        get() = IconLoader.getIcon("/icons/pause.svg", javaClass)
    val PLAY: Icon
        get() = IconLoader.getIcon("/icons/play.svg", javaClass)
    val POWER: Icon
        get() = IconLoader.getIcon("/icons/power.svg", javaClass)
    val REWIND: Icon
        get() = IconLoader.getIcon("/icons/rewind.svg", javaClass)
    val STOP: Icon
        get() = IconLoader.getIcon("/icons/stop.svg", javaClass)
}
