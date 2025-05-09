package com.hyen.smartportfolio_plus.data.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hyen.smartportfolio_plus.data.contact.Contact

object FireStoreContactService {
    private val db = Firebase.firestore

    fun addContact(contact: Contact, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("contacts")
            .add(contact)
            .addOnSuccessListener { onSuccess(it.id) }
            .addOnFailureListener { onFailure(it) }
    }

    fun updateContact(contact: Contact, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val id = contact.firestoreId ?: return onFailure(Exception("No ID"))
        db.collection("contacts")
            .document(id)
            .set(contact)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun deleteContact(firestoreId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("contacts")
            .document(firestoreId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getContacts(onResult: (List<Contact>) -> Unit) {
        db.collection("contacts")
            .get()
            .addOnSuccessListener { result ->
                val contacts = result.documents.mapNotNull {
                    it.toObject(Contact::class.java)?.copy(firestoreId = it.id)
                }
                onResult(contacts)
            }
    }
}