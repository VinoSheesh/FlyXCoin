package com.example.flyxcoin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
    companion object {
        private val _language = MutableStateFlow("en")
        val language: StateFlow<String> = _language.asStateFlow()
    }

    fun setLanguage(lang: String) {
        _language.value = lang
    }

    fun getLanguage(): StateFlow<String> = language
}