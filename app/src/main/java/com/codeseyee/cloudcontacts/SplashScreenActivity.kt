package com.codeseye.cloudcontacts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.codeseye.cloudcontacts.utils.PreferenceManger
import com.codeseye.cloudcontacts.utils.AppUtils
import com.codeseyee.cloudcontacts.LoginActivity
import com.codeseyee.cloudcontacts.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var layout: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(layout.root)

        val preferredMode = PreferenceManger.getPreferredMode()
        if (preferredMode != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM &&
            AppUtils.isNightMode() != (preferredMode == AppCompatDelegate.MODE_NIGHT_YES)) {
            AppCompatDelegate.setDefaultNightMode(preferredMode)
            return
        }

        // Delayed navigation based on user authentication
        val handler = Handler(Looper.getMainLooper())
        if (PreferenceManger.getCurrentUser() == null) {
            handler.postDelayed({
                navigateToLogin()
            }, 1500)
        } else {
            handler.postDelayed({
                navigateToMain()
            }, 1500)
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        finish()
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        finish()
    }
}
