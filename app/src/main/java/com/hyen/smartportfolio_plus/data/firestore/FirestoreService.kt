package com.hyen.smartportfolio_plus.data.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hyen.smartportfolio_plus.data.project.Project

object FirestoreService {
    private val db = Firebase.firestore

    fun addProject(project: Project, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("projects")
            .add(project)
            .addOnSuccessListener { onSuccess(it.id) }
            .addOnFailureListener { onFailure(it) }
    }

    fun updateProject(project: Project, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val id = project.firestoreId ?: return onFailure(Exception("No ID"))
        db.collection("projects")
            .document(id)
            .set(project)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun deleteProject(firestoreId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("projects")
            .document(firestoreId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getProjects(onResult: (List<Project>) -> Unit) {
        db.collection("projects")
            .get()
            .addOnSuccessListener { result ->
                val projects = result.documents.mapNotNull {
                    it.toObject(Project::class.java)?.copy(firestoreId = it.id)
                }
                onResult(projects)
            }
    }
}