package com.hyen.smartportfolio_plus.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.hyen.smartportfolio_plus.data.contact.Contact
import com.hyen.smartportfolio_plus.data.contact.ContactDatabase
import com.hyen.smartportfolio_plus.data.repository.ContactRepository
import kotlinx.coroutines.launch


// viewmodel - repository - DAO - DB

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    // repository 선언
    private val repository: ContactRepository

    // 전체 메시지 목록 관찰하는 LiveData
    val allContacts: LiveData<List<Contact>>

    init {
        // DB 인스턴스 가져오기
        val contactDao = ContactDatabase.getDatabase(application).contactDao()

        // Repository 연결
        repository = ContactRepository(contactDao)

        // Flow -> LiveData로 변환해서 UI에서 쉽게 관찰 가능
        allContacts = repository.allContacts.asLiveData()
    }

    // 메시지 추가
    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    // 메시지 수정
    fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)
    }

    // 메시지 삭제
    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
    }
}