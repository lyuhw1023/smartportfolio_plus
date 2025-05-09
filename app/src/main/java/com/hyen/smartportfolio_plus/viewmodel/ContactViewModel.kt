package com.hyen.smartportfolio_plus.viewmodel

import androidx.lifecycle.*
import com.hyen.smartportfolio_plus.data.contact.Contact
import com.hyen.smartportfolio_plus.data.firestore.FireStoreContactRepository
import com.hyen.smartportfolio_plus.data.repository.ContactRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ContactViewModel(
    private val roomRepo: ContactRepository,
    private val cloudRepo: FireStoreContactRepository
) : ViewModel() {

    val localContact: LiveData<List<Contact>> = roomRepo.allContacts.asLiveData()
    private val _error = MutableLiveData<String?>()

    init {
        // 앱 시작 시, 아직 Firestore 에 동기화되지 않은 로컬 항목만 가져와서 동기화
        viewModelScope.launch {
            val unsynced = roomRepo.allContacts.first()
                .filter { it.firestoreId == null }
            unsynced.forEach { contact ->
                cloudRepo.insert(contact, { firestoreId ->
                    // 동기화 성공 시, local DB 에 firestoreId 업데이트
                    viewModelScope.launch {
                        roomRepo.update(contact.copy(firestoreId = firestoreId))
                    }
                }, { ex ->
                    _error.postValue("Firestore insert 오류: ${ex.message}")
                })
            }
        }
    }

    fun insertLocal(contact: Contact) = viewModelScope.launch {
        // 로컬에 먼저 저장
        roomRepo.insert(contact)
        // 방금 저장된 항목
        val inserted = roomRepo.allContacts.first()
            .find { it.timestamp == contact.timestamp && it.firestoreId == null }
            ?: return@launch

        // Firestore에 등록하고, 완료되면 로컬에 firestoreId 업데이트
        cloudRepo.insert(inserted, { id ->
            viewModelScope.launch {
                roomRepo.update(inserted.copy(firestoreId = id))
            }
        }, { ex ->
            _error.postValue("Firestore insert 오류: ${ex.message}")
        })
    }

    fun updateLocal(contact: Contact) = viewModelScope.launch {
        // 로컬에 먼저 업데이트
        roomRepo.update(contact)
        // 이미 firestoreId 있으면 cloud 갱신
        contact.firestoreId?.let { id ->
            cloudRepo.update(contact, {}, { ex ->
                _error.postValue("Firestore update 오류: ${ex.message}")
            })
        }
    }

    fun deleteLocal(contact: Contact) = viewModelScope.launch {
        // cloud 먼저 삭제
        contact.firestoreId?.let { id ->
            cloudRepo.delete(id, {}, { ex ->
                _error.postValue("Firestore delete 오류: ${ex.message}")
            })
        }
        // 이후 로컬 삭제
        roomRepo.delete(contact)
    }
}