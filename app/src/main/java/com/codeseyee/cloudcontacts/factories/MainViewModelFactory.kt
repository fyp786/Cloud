package com.codeseyee.cloudcontacts.factories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeseye.cloudcontacts.Repository.UserRepository
import com.codeseye.cloudcontacts.viewmodels.MainViewModel

class MainViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
