package com.hyen.smartportfolio_plus.data.firestore

import com.hyen.smartportfolio_plus.data.project.Project

class FireStoreProjectRepository {
    fun getAll(onResult: (List<Project>) -> Unit) =
        FireStoreProjectService.getProjects(onResult)

    fun insert(
        project: Project,
        onComplete: (String) -> Unit,
        onError: (Exception) -> Unit
    ) = FireStoreProjectService.addProject(project, onComplete, onError)

    fun update(
        project: Project,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) = FireStoreProjectService.updateProject(project, onComplete,onError)

    fun delete(
        firestoreId: String,
        onComplete: () -> Unit,
        onError: (Exception) -> Unit
    ) = FireStoreProjectService.deleteProject(firestoreId, onComplete, onError)
}