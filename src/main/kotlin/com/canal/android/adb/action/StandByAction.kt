package com.canal.android.adb.action

class StandByAction : BaseShellAction() {

    override fun getShellCommand(): String = "am start -n \"com.android.systemui/.Somnambulator\""

}

