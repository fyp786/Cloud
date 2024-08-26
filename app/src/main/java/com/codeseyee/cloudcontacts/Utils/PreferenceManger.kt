package com.codeseye.cloudcontacts.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.codeseye.cloudcontacts.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object PreferenceManger {

    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var generalPrefs: SharedPreferences
    private lateinit var generalEditor: SharedPreferences.Editor

    private val stringTypeToken: Type = object : TypeToken<ArrayList<String>>() {}.type
    private val longTypeToken: Type = object : TypeToken<ArrayList<Long>>() {}.type

    private const val pinned = "Pinned"
    private const val savedCards = "SavedCards"

    init {
        val context = MyApplication.context
        generalPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        generalEditor = generalPrefs.edit()
        prefs = context.getSharedPreferences("prefsv1${currentUser?.id ?: 1}", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun registerOnPrefsChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    var preferredMode: Int
        get() = prefs.getInt("PreferredMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        set(value) = editor.putInt("PreferredMode", value).apply()

    var fvtContactsNeedSync: Boolean
        get() = prefs.getBoolean("fvtContactsNeedSync", false)
        set(value) = editor.putBoolean("fvtContactsNeedSync", value).apply()

    var fvtNeedSync: Boolean
        get() = prefs.getBoolean("fvtNeedSync", false)
        set(value) = editor.putBoolean("fvtNeedSync", value).apply()

    fun saveToRecentlyScannedCards(value: Long) {
        removeFromRecentlyScannedCards(value)
        val list = getRecentlyScannedCards().toMutableList()
        list.add(0, value)
        if (list.size > 30) list.removeAt(list.size - 1)
        saveRecentlyScannedCards(list)
    }

    fun removeFromRecentlyScannedCards(value: Long) {
        val list = getRecentlyScannedCards().toMutableList()
        list.remove(value)
        saveRecentlyScannedCards(list)
    }
    fun getCurrentUser(): User? {
        return Gson().fromJson(generalPrefs.getString("user", ""), User::class.java)
    }

    fun setCurrentUser(value: User?) {
        generalEditor.putString("user", Gson().toJson(value)).apply()
    }


    fun isInRecentlyScannedCards(value: Long): Boolean {
        return getRecentlyScannedCards().contains(value)
    }

    fun saveRecentlyScannedCards(value: List<Long>) {
        editor.putString("RecentlyScannedCards", Gson().toJson(value)).apply()
    }

    fun getRecentlyScannedCards(): List<Long> {
        return Gson().fromJson(prefs.getString("RecentlyScannedCards", "[]"), longTypeToken)
    }

    fun saveToSavedCards(value: Long) {
        removeFromSavedCards(value)
        val list = getSavedCards().toMutableList()
        list.add(0, value)
        saveSavedCards(list)
    }

    fun removeFromSavedCards(value: Long) {
        val list = getSavedCards().toMutableList()
        list.remove(value)
        saveSavedCards(list)
    }

    fun isInSavedCards(value: Long): Boolean {
        return getSavedCards().contains(value)
    }

    fun saveSavedCards(value: List<Long>) {
        editor.putString(savedCards, Gson().toJson(value)).apply()
    }

    fun getSavedCards(): List<Long> {
        return Gson().fromJson(prefs.getString(savedCards, "[]"), longTypeToken)
    }

    fun saveToPinned(value: Long) {
        removeFromPinned(value)
        val list = getPinned().toMutableList()
        list.add(0, value)
        savePinned(list)
    }

    fun removeFromPinned(value: Long) {
        val list = getPinned().toMutableList()
        list.remove(value)
        savePinned(list)
    }

    fun isPinned(value: Long): Boolean {
        return getPinned().contains(value)
    }

    fun savePinned(value: List<Long>) {
        editor.putString(pinned, Gson().toJson(value)).apply()
    }

    fun getPinned(): List<Long> {
        return Gson().fromJson(prefs.getString(pinned, "[]"), longTypeToken)
    }

    fun saveToRecent(value: Long) {
        removeFromRecent(value)
        val list = getRecent().toMutableList()
        list.add(0, value)
        if (list.size > 30) list.removeAt(list.size - 1)
        saveRecent(list)
    }

    fun removeFromRecent(value: Long) {
        val list = getRecent().toMutableList()
        list.remove(value)
        saveRecent(list)
    }

    fun isRecent(value: Long): Boolean {
        return getRecent().contains(value)
    }

    fun saveRecent(value: List<Long>) {
        editor.putString("Recent", Gson().toJson(value)).apply()
    }

    fun getRecent(): List<Long> {
        return Gson().fromJson(prefs.getString("Recent", "[]"), longTypeToken)
    }

    var apiToken: String
        get() = generalPrefs.getString("api_token", "none") ?: "none"
        set(value) = generalEditor.putString("api_token", value).apply()

    var currentUser: User?
        get() = Gson().fromJson(generalPrefs.getString("user", ""), User::class.java)
        set(value) = generalEditor.putString("user", Gson().toJson(value)).apply()

    fun removeCurrentUser() {
        generalEditor.remove("user").apply()
    }

    var lastLoadDate: Long
        get() = prefs.getLong("loadUsersWhichAreContacts", 0L)
        set(_) = editor.putLong("loadUsersWhichAreContacts", System.currentTimeMillis()).apply()

    // Add this method to get the preferred mode
    fun getPreferredMode(): Int {
        return preferredMode
    }

    // Add this method to check if the current mode is night mode
    fun isNightMode(context: Context): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }
}
