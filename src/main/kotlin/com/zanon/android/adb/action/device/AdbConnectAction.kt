package com.zanon.android.adb.action.device

class AdbConnectAction : BaseDeviceConnectionAction() {

    override fun getAdbCommand(ipAddress: String): String = "connect $ipAddress"

}