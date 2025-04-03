package com.hyen.smartportfolio_plus.data.repository

import com.hyen.smartportfolio_plus.data.contact.Contact
import com.hyen.smartportfolio_plus.data.contact.ContactDao
import kotlinx.coroutines.flow.Flow

// viewmodel - repository - DAO - DB

// viewmodel - DAO 사이에서 데이터 처리 담당
class ContactRepository(private val contactDao: ContactDao) {

    // 모든 메시지 불러오는 함수
    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()

    // 메시지 저장
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    // 메시지 수정
    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    // 메시지 삭제
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
}