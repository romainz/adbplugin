package com.zanon.android.adb.action.application

import com.android.ddmlib.IDevice
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.zanon.android.adb.util.toCurrentDevice

/**
 * Choose an apk file and install it on the selected device
 */
class InstallAction : com.zanon.android.adb.action.BaseAdbAction() {

    private lateinit var device: IDevice
    private lateinit var apkPath: String

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.toCurrentDevice()?.let { device ->
            this.device = device

            val apkFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileDescriptor(), null, null)
            apkFile?.let { file ->
                apkPath = file.path
                super.actionPerformed(event)
            }
        }
    }

    override fun getAdbCommand(): String = "-s ${device.serialNumber} install $apkPath"

}

