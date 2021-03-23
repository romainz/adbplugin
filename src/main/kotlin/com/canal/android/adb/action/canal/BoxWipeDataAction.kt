package com.canal.android.adb.action.canal

import com.canal.android.adb.action.BaseShellAction

class BoxWipeDataAction : BaseShellAction() {

    override fun getShellCommand(): String = "pm clear com.canal.box"

}

