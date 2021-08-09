package com.zanon.android.adb.setting

import com.zanon.android.adb.setting.model.Application
import com.zanon.android.adb.setting.model.Device
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.XCollection
import com.zanon.android.adb.setting.model.Deeplink
import com.zanon.android.adb.setting.model.InputText


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

    @XCollection(elementName = "deeplinks")
    var deeplinks: List<Deeplink> = emptyList()

    @XCollection(elementName = "inputTexts")
    var inputTexts: List<InputText> = emptyList()

    override fun getState(): AdbPluginSettingsState = this

    override fun loadState(state: AdbPluginSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AdbPluginSettingsState
            get() = ServiceManager.getService(AdbPluginSettingsState::class.java)
    }
}