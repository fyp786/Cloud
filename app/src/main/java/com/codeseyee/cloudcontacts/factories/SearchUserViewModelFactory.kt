package com.codeseyee.cloudcontacts.factories


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeseyee.cloudcontacts.ViewModel.SearchUserViewModel
import com.codeseyee.cloudcontacts.room.AppDatabase

class SearchUserViewModelFactory(
    private val application: Application,
    private val appDatabase: AppDatabase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
            return SearchUserViewModel(application, appDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
