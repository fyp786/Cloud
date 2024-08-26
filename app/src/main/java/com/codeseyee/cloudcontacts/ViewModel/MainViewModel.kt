package com.codeseye.cloudcontacts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codeseyee.cloudcontacts.Models.User
import com.codeseyee.cloudcontacts.Repository.UserRepository

class MainViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    fun loadUserData() {
        val user = userRepository.getCurrentUser()
        _userLiveData.postValue(user)
    }

    fun logout() {
        userRepository.logout()
    }

    fun toggleDarkMode() {
        userRepository.toggleDarkMode()
    }
}
