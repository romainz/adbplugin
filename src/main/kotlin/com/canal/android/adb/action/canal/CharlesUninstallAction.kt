package com.canal.android.adb.action.canal

import com.canal.android.adb.action.BaseShellAction

class CharlesUninstallAction : BaseShellAction() {

    override fun getShellCommand(): String = "uninstall com.canal.android.canal.charles"

}

