package com.codeseye.cloudcontacts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codeseye.cloudcontacts.models.User
import com.codeseye.cloudcontacts.retrofit.UsersDatabaseProvider
import com.codeseyee.cloudcontacts.ContactUtlis.ContactHelper
import com.codeseyee.cloudcontacts.Utils.UsersAndContactsHelper
import com.codeseyee.cloudcontacts.room.AppDatabase
import com.google.gson.Gson

class UserDetailsViewModel : ViewModel() {

    private val usersDatabaseProvider = UsersDatabaseProvider() // Pass context if needed
    private val appDatabase = AppDatabase.getDatabase() // Use application context to initialize

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun setUser(userJson: String) {
        _user.value = Gson().fromJson(userJson, User::class.java)
    }

    fun refreshUserData(userId: Long, onComplete: (Boolean, User?, Throwable?) -> Unit) {
        usersDatabaseProvider.loadUser(userId) { isSuccessful, data, error ->
            if (isSuccessful && data is User) {
                _user.value = data
                appDatabase.userDao().insertOrUpdate(data)
            }
            onComplete(isSuccessful, data as? User, error)
        }
    }

    fun toggleFvt(user: User): Boolean {
        return UsersAndContactsHelper().toggleFvt(user)
    }

    fun saveToContact(user: User): Boolean {
        return ContactHelper().saveToContact(user)
    }

    fun isUserContact(phoneNumber: String): Boolean {
        return appDatabase.contactDao().phoneExists(phoneNumber)
    }
}
