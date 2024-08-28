package com.codeseyee.cloudcontacts.ViewModel


import androidx.lifecycle.ViewModel
import com.codeseyee.cloudcontacts.retrofit.UsersDatabaseProvider

class SettingsViewModel : ViewModel() {

    private val usersDatabaseProvider = UsersDatabaseProvider()

    fun deactivateAccount(onComplete: (Boolean, Any?, Throwable?) -> Unit) {
        usersDatabaseProvider.deactivateAccount { isSuccessful, data, error ->
            onComplete(isSuccessful, data, error)
        }
    }
}
