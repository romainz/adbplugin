package com.canal.android.adb.action.device

class AdbConnectAction : BaseDeviceConnectionAction() {

    override fun getAdbCommand(ipAddress: String): String {
        return "connect $ipAddress"
    }
}