package com.canal.android.adb.setting

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil


/**
 * Supports storing the application settings in a persistent way.
 * The [State] and [Storage] annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
    name = "com.canal.android.adb.setting.AdbPluginSettingsState",
    storages = [Storage("AdbPluginSettings.xml")]
)
class AdbPluginSettingsState : PersistentStateComponent<AdbPluginSettingsState?> {

    var displayAdbNotification = false
    var applications : List<String> = emptyList()

    override fun getState(): AdbPluginSettingsState = this

    override fun loadState(state: AdbPluginSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AdbPluginSettingsState
            get() = ServiceManager.getService(AdbPluginSettingsState::class.java)
    }
}