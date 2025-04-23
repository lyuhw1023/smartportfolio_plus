package com.hyen.smartportfolio_plus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hyen.smartportfolio_plus.data.firestore.FireStoreProjectRepository
import com.hyen.smartportfolio_plus.data.repository.ProjectRepository

class ProjectViewModelFactory(
    private val roomRepo: ProjectRepository,
    private val cloudRepo: FireStoreProjectRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(roomRepo, cloudRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}