package com.codeseyee.cloudcontacts.ViewModel

package com.codeseye.cloudcontacts.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codeseye.cloudcontacts.contactutils.Contact
import com.codeseye.cloudcontacts.models.User
import com.codeseye.cloudcontacts.room.AppDatabase
import com.codeseye.cloudcontacts.utils.AppExecutor
import com.codeseye.cloudcontacts.utils.PreferenceManger
import kotlinx.coroutines.launch

class SearchUserViewModel(
    application: Application,
    private val appDatabase: AppDatabase
) : AndroidViewModel(application) {

    private val _resultsUsers = MutableLiveData<List<Any>>()
    val resultsUsers: LiveData<List<Any>> get() = _resultsUsers

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean> get() = _progressBarVisibility

    private var key: String = ""
    private val tempUser = mutableListOf<User>()
    private val tempContacts = mutableListOf<Contact>()

    fun search(searchKey: String) {
        key = searchKey.trim()
        viewModelScope.launch {
            AppExecutor.getInstance().diskIO().execute {
                tempContacts.clear()
                if (key.isNotEmpty()) {
                    tempContacts.addAll(appDatabase.contactDao().search("%${key.replace(" ", "%")}%"))
                }
                tempUser.clear()
                tempUser.addAll(appDatabase.userDao().search("%${key.replace(" ", "%")}%", PreferenceManger.getCurrentUser().id))
                notifyAdapter()
            }
        }
    }

    fun searchOnServer(searchKey: String) {
        if (searchKey.trim().isEmpty()) {
            _progressBarVisibility.value = false
            return
        }
        _progressBarVisibility.value = true
        // Replace with your server call
        UsersDatabaseProvider(getApplication()).searchUser(searchKey.replace(" ", "%20")) { isSuccessful, data, error ->
            _progressBarVisibility.value = false
            // Handle the server response
        }
    }

    private fun notifyAdapter() {
        val results = mutableListOf<Any>()
        if (key.isNotEmpty()) results.add("label:Your contacts (${tempContacts.size})")
        results.addAll(tempContacts)
        if (key.isNotEmpty()) results.add("label:Cloud contacts (${tempUser.size})")
        results.addAll(tempUser)
        results.add("label:Press search button to search from server-divider")
        _resultsUsers.value = results
    }
}
