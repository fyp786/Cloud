package com.codeseyee.cloudcontacts.ViewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeseye.cloudcontacts.models.User
import com.codeseyee.cloudcontacts.Repository.ScannerRepository
import kotlinx.coroutines.launch

class ScannerViewModel(private val scannerRepository: ScannerRepository) : ViewModel() {

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> get() = _userLiveData

    fun scanQRImage(bitmap: Bitmap) {
        viewModelScope.launch {
            val qrResult = scannerRepository.scanQRImage(bitmap)
            val user = qrResult?.let { scannerRepository.handleQRCodeResult(it) }
            _userLiveData.postValue(user)
        }
    }
}
