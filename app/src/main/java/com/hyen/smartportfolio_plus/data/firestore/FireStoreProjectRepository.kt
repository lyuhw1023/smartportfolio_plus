package com.hyen.smartportfolio_plus.data.firestore

import com.hyen.smartportfolio_plus.data.project.Project

class FireStoreProjectRepository {
    fun getAll(onResult: (List<Project>) -> Unit) =
        FirestoreService.getProjects(onResult)

    fun insert(
        project: Project,
        onComplete: (String) -> Unit,
        onError: (Exception) -> Unit
    ) = FirestoreService.addProject(project, onComplete, onError)

    fun update(
        project: Project,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) = FirestoreService.updateProject(project, onComplete,onError)

    fun delete(
        firestoreId: String,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) = FirestoreService.deleteProject(firestoreId, onComplete, onError)
}