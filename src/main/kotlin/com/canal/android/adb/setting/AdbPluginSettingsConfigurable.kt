package com.canal.android.adb.setting

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class AdbPluginSettingsConfigurable : Configurable {

    private var settingsComponent: AdbPluginSettingsComponent? = null

    override fun getDisplayName(): String = "ADB+ Settings"

    override fun createComponent(): JComponent? {
        settingsComponent = AdbPluginSettingsComponent()
        return settingsComponent?.panel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return settingsComponent?.preferredFocusedComponent
    }

    override fun isModified(): Boolean {
        val settings: AdbPluginSettingsState = AdbPluginSettingsState.instance
        return settingsComponent?.displayAdbNotification != settings.displayAdbNotification
    }

    override fun apply() {
        val settings: AdbPluginSettingsState = AdbPluginSettingsState.instance
        settings.displayAdbNotification = settingsComponent?.displayAdbNotification ?: false
    }

    override fun reset() {
        val settings: AdbPluginSettingsState = AdbPluginSettingsState.instance
        settingsComponent?.displayAdbNotification = settings.displayAdbNotification
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }

}