package com.hyen.smartportfolio_plus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hyen.smartportfolio_plus.data.firestore.FireStoreContactRepository
import com.hyen.smartportfolio_plus.data.repository.ContactRepository

class ContactViewModelFactory(
    private val roomRepo: ContactRepository,
    private val cloudRepo: FireStoreContactRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(roomRepo, cloudRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}