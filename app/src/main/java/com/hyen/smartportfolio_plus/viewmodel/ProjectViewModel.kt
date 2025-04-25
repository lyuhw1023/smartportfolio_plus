package com.hyen.smartportfolio_plus.viewmodel

import androidx.lifecycle.*
import com.hyen.smartportfolio_plus.data.firestore.FireStoreProjectRepository
import com.hyen.smartportfolio_plus.data.project.Project
import com.hyen.smartportfolio_plus.data.repository.ProjectRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProjectViewModel(
    private val roomRepo: ProjectRepository,
    private val cloudRepo: FireStoreProjectRepository
) : ViewModel() {

    val localProject: LiveData<List<Project>> = roomRepo.allProjects.asLiveData()
    private val _cloudProjects = MutableLiveData<List<Project>>()
    val cloudProject: LiveData<List<Project>> = _cloudProjects

    fun loadCloudProjects() {
        cloudRepo.getAll { _cloudProjects.value = it }
    }

    fun insertLocal(project: Project) = viewModelScope.launch {
        roomRepo.insert(project)
    }

    fun insertCloud(project: Project) {
        cloudRepo.insert(project, { firestoreId ->
            val updated = project.copy(firestoreId = firestoreId)
            insertLocal(updated)
        }, { error ->
            insertLocal(project)
            error.printStackTrace()
        })
    }

    fun updateLocal(project: Project) = viewModelScope.launch {
        roomRepo.update(project)
    }

    fun updateCloud(project: Project) {
        project.firestoreId?.let {
            cloudRepo.update(project, {
                loadCloudProjects()
            }, {
                it.printStackTrace()
            })
        }
    }

    fun deleteLocal(project: Project) = viewModelScope.launch {
        roomRepo.delete(project)
    }

    fun deleteCloud(project: Project) {
        project.firestoreId?.let {
            cloudRepo.delete(it, {
                loadCloudProjects()
            }, {
                it.printStackTrace()
            })
        }
    }

    fun syncLocalToCloud() = viewModelScope.launch {
        roomRepo.allProjects
            .first()
            .filter { it.firestoreId == null }
            .forEach { project ->
                cloudRepo.insert(project, { firestoreId ->
                    val updatedProject = project.copy(firestoreId = firestoreId)
                    viewModelScope.launch {
                        roomRepo.update(
                            project.copy(firestoreId = firestoreId)
                        )
                    }
                }, { error ->
                    error.printStackTrace()
                })
            }
    }
}