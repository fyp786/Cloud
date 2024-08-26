package com.codeseye.cloudcontacts.Repository

import androidx.appcompat.app.AppCompatDelegate
import com.codeseye.cloudcontacts.utils.PreferenceManger
import com.codeseye.cloudcontacts.models.User

class UserRepository {

    fun getCurrentUser(): User? {
        return PreferenceManger.getCurrentUser()
    }

    fun logout() {
        // Clear user data from preferences
        PreferenceManger.setCurrentUser(null)
        // Google Sign-Out logic (assuming you're using Google Sign-In)
        // Implement your Google Sign-Out logic here if applicable
    }

    fun toggleDarkMode() {
        val currentMode = PreferenceManger.getPreferredMode()
        val newMode = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        PreferenceManger.preferredMode = newMode
        AppCompatDelegate.setDefaultNightMode(newMode)
    }
}
