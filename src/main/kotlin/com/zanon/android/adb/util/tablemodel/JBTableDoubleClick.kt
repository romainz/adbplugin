package com.zanon.android.adb.util.tablemodel

import com.intellij.ui.table.JBTable
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.table.TableModel

class JBTableDoubleClick(model: TableModel) : JBTable(model) {

    fun addRowListener(simpleClick: (Int) -> Unit, doubleClick: (Int) -> Unit) {
        addMouseListener(object : MouseAdapter() {

            override fun mouseClicked(e: MouseEvent?) {
                when (e?.clickCount) {
                    1 -> simpleClick(selectedRow)
                    2 -> doubleClick(selectedRow)
                }
            }
        })
    }
}