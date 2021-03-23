package com.canal.android.adb.action.canal

import com.canal.android.adb.action.BaseShellAction

class BoxUninstallAction : BaseShellAction() {

    override fun getShellCommand(): String = "uninstall com.canal.box"

}

