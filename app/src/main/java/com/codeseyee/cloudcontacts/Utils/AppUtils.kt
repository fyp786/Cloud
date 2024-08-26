package com.codeseye.cloudcontacts.utils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.support.v4.BuildConfig
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import com.codeseye.cloudcontacts.contactutils.Contact
import com.codeseye.cloudcontacts.models.User
import com.codeseyee.cloudcontacts.BusinessContactDetails
import com.codeseyee.cloudcontacts.ContactDetails
import com.codeseyee.cloudcontacts.EditBusinessProfileActvity
import com.codeseyee.cloudcontacts.EditIndividualProfileActvity
import com.codeseyee.cloudcontacts.R
import com.codeseyee.cloudcontacts.UserDetailsActivity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.Gson
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    // Move this function to be a top-level function
    fun isNightMode(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    fun showProgressDialog(context: Context, title: String, message: String): Dialog {
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null)
        view.findViewById<TextView>(R.id.progress_title).apply {
            text = title
            visibility = View.VISIBLE
        }
        view.findViewById<TextView>(R.id.progress_message).text = message
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }

    fun showMessageDialog(context: Context, title: String, message: String, listener: OnDialogResultListener?) {
        AlertDialog.Builder(context, R.style.AlertDialogTheme).apply {
            setMessage(message)
            setTitle(title)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                listener?.onYesChosen()
            }
            show()
        }
    }

    fun showYesNoDialog(context: Context, title: String, message: String, listener: OnDialogResultListener?) {
        AlertDialog.Builder(context, R.style.AlertDialogTheme).apply {
            setMessage(message)
            setTitle(title)
            setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                listener?.onYesChosen()
            }
            setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    // ... (rest of your methods)

    interface OnDialogResultListener {
        fun onYesChosen()
        fun onNoChosen()
    }
}
