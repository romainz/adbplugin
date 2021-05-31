package com.canal.android.adb.setting

import com.canal.android.adb.setting.model.Application
import com.canal.android.adb.setting.model.Device
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.XCollection


/**
 * Supports storing the application settings in a persistent way.
 * The [State] and [Storage] annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
    name = "ADB+ Settings",
    storages = [Storage("AdbPluginSettings.xml")]
)
class AdbPluginSettingsState : PersistentStateComponent<AdbPluginSettingsState?> {

    var displayAdbNotification = false

    @XCollection(elementName = "applications")
    var applications: List<Application> = emptyList()

    @XCollection(elementName = "devices")
    var devices: List<Device> = emptyList()

    override fun getState(): AdbPluginSettingsState = this

    override fun loadState(state: AdbPluginSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AdbPluginSettingsState
            get() = ServiceManager.getService(AdbPluginSettingsState::class.java)
    }
}