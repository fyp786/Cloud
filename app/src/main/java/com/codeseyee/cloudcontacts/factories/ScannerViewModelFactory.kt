package com.codeseyee.cloudcontacts.factories


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeseyee.cloudcontacts.Repository.ScannerRepository
import com.codeseyee.cloudcontacts.ViewModel.ScannerViewModel

class ScannerViewModelFactory(private val scannerRepository: ScannerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScannerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScannerViewModel(scannerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
