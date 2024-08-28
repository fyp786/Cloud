package com.codeseyee.cloudcontacts.factories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeseyee.cloudcontacts.ViewModel.SettingsViewModel

class SettingsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
