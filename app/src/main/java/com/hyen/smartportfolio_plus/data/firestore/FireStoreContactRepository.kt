package com.hyen.smartportfolio_plus.data.firestore

import com.hyen.smartportfolio_plus.data.contact.Contact

class FireStoreContactRepository {
    fun getAll(onResult: (List<Contact>) -> Unit) =
        FireStoreContactService.getContacts(onResult)

    fun insert(
        contact: Contact,
        onComplete: (String) -> Unit,
        onError: (Exception) -> Unit
    ) = FireStoreContactService.addContact(contact, onComplete, onError)

    fun update(
        contact: Contact,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) = FireStoreContactService.updateContact(contact, onComplete, onError)

    fun delete(
        firestoreId: String,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) = FireStoreContactService.deleteContact(firestoreId, onComplete, onError)
}