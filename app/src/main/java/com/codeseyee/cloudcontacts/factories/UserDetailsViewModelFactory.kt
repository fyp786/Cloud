package com.codeseyee.cloudcontacts.factories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeseye.cloudcontacts.viewmodels.UserDetailsViewModel

class UserDetailsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
