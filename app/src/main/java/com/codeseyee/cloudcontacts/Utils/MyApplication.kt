package com.codeseye.cloudcontacts.utils

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        // Initialize PreferenceManager if necessary
        // Example: PreferenceManager.initialize(context)
    }
}
