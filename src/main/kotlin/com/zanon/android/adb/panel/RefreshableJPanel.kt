package com.zanon.android.adb.panel

import java.awt.LayoutManager
import javax.swing.JPanel

abstract class RefreshableJPanel : JPanel {

    constructor() : super()

    constructor(layout: LayoutManager) : super(layout)

    abstract fun refresh()
}