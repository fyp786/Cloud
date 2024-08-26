package com.codeseyee.cloudcontacts

import android.app.Dialog
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codeseye.cloudcontacts.models.User
import com.codeseye.cloudcontacts.utils.AppUtils
import com.codeseyee.cloudcontacts.ViewModel.SignUpViewModel
import com.codeseyee.cloudcontacts.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private var profileImageUri: Uri? = null
    private lateinit var loadingDialog: Dialog

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                profileImageUri = uri
                binding.profileImage.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog = AppUtils.showProgressDialog(this, "Processing", "Please wait...")

        // Underline Signup Text
        binding.signupTxt.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.profileImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.signup.setOnClickListener {
            if (validateInput()) {
                val user = User().apply {
                    name = binding.name.editText?.text.toString()
                    email = binding.email.editText?.text.toString()
                    phoneNumber = binding.phone.editText?.text.toString()
                    isBusinessAccount = binding.chipBusiness.isChecked
                    isPublic = binding.publicProfile.isChecked
                }

                loadingDialog.show()
                viewModel.signUpUser(
                    user.toString(),
                    binding.password.editText?.text.toString(),
                    profileImageUri.toString()
                )
            }
        }

        viewModel.signupResult.observe(this) { result ->
            loadingDialog.dismiss()
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
            if (result == "Signup successful") {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        if (profileImageUri == null) {
            Toast.makeText(this, "Select a profile picture", Toast.LENGTH_LONG).show()
            return false
        }
        if (binding.email.editText?.text.toString().trim().isEmpty()) {
            binding.email.editText?.error = "Enter email"
            binding.email.requestFocus()
            return false
        }
        if (binding.phone.editText?.text.toString().trim().isEmpty()) {
            binding.phone.editText?.error = "Enter phone number"
            binding.phone.requestFocus()
            return false
        }
        if (binding.name.editText?.text.toString().trim().isEmpty()) {
            binding.name.editText?.error = "Enter name"
            binding.name.requestFocus()
            return false
        }
        if (binding.password.editText?.text.toString().trim().isEmpty()) {
            binding.password.editText?.error = "Enter password"
            binding.password.requestFocus()
            return false
        }
        if (binding.password.editText?.text.toString().trim().length < 6) {
            binding.password.editText?.error = "Password must be at least 6 characters"
            binding.password.requestFocus()
            return false
        }
        return true
    }
}
