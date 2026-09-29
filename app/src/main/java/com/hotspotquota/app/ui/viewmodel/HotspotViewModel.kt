package com.hotspotquota.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HotspotViewModel(...) : ViewModel() {
    companion object {
        const val MAX_DEVICES = 5
    }

    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _appLanguage = MutableStateFlow("fr") // "fr", "en", "es"
    val appLanguage: StateFlow<String> = _appLanguage

    fun toggleDarkMode() { _isDarkMode.value = !_isDarkMode.value }
    fun setLanguage(lang: String) { _appLanguage.value = lang }
}