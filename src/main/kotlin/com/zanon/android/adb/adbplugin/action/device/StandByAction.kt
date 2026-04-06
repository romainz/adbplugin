package com.zanon.android.adb.action.device

import com.zanon.android.adb.action.BaseShellAction

class StandByAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -n \"com.android.systemui/.Somnambulator\""

}

