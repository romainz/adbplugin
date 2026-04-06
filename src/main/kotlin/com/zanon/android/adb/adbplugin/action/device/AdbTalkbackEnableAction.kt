package com.zanon.android.adb.action.device

import com.zanon.android.adb.action.BaseShellAction

class AdbTalkbackEnableAction : BaseShellAction() {

    override fun getShellCommand(): String = "settings put secure enabled_accessibility_services com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService"

}