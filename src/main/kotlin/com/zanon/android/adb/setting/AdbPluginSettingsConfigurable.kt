package com.zanon.android.adb.setting

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class AdbPluginSettingsConfigurable : Configurable {

    private var settingsComponent: AdbPluginSettingsComponent? = null

    override fun getDisplayName(): String = "ADB Tools Settings"

    override fun createComponent(): JComponent? {
        settingsComponent = AdbPluginSettingsComponent()
        return settingsComponent?.panel
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return settingsComponent?.preferredFocusedComponent
    }

    override fun isModified(): Boolean {
        val settings: AdbPluginSettingsState = AdbPluginSettingsState.instance
        return (settingsComponent?.displayAdbNotification != settings.displayAdbNotification)
                || (settingsComponent?.applications != settings.applications)
                || (settingsComponent?.devices != settings.devices)
                || (settingsComponent?.deeplinks != settings.deeplinks)
                || (settingsComponent?.inputTexts != settings.inputTexts)
    }

    override fun apply() {
        val settings: AdbPluginSettingsState = AdbPluginSettingsState.instance
        settings.displayAdbNotification = settingsComponent?.displayAdbNotification ?: false
        settings.applications = settingsComponent?.applications ?: mutableListOf()
        settings.devices = settingsComponent?.devices ?: mutableListOf()
        settings.deeplinks = settingsComponent?.deeplinks ?: mutableListOf()
        settings.inputTexts = settingsComponent?.inputTexts ?: mutableListOf()
    }

    override fun reset() {
        val settings: AdbPluginSettingsState = AdbPluginSettingsState.instance
        settingsComponent?.displayAdbNotification = settings.displayAdbNotification
        settingsComponent?.applications = settings.applications
        settingsComponent?.devices = settings.devices
        settingsComponent?.deeplinks = settings.deeplinks
        settingsComponent?.inputTexts = settings.inputTexts
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }

}