package com.hyen.smartportfolio_plus.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.hyen.smartportfolio_plus.data.project.Project
import com.hyen.smartportfolio_plus.data.project.ProjectDatabase
import com.hyen.smartportfolio_plus.data.repository.ProjectRepository
import kotlinx.coroutines.launch


class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProjectRepository

    // 전체 프로젝트 목록 LiveData로 노출
    val allProjects: LiveData<List<Project>>

    init {
        val projectDao = ProjectDatabase.getDatabase(application).projectDao()
        repository = ProjectRepository(projectDao)
        allProjects = repository.allProjects.asLiveData()
    }

    // 추가
    fun insert(project: Project) = viewModelScope.launch {
        repository.insert(project)
    }
    // 수정
    fun update(project: Project) = viewModelScope.launch {
        repository.update(project)
    }
    // 삭제
    fun delete(project: Project) = viewModelScope.launch {
        repository.delete(project)
    }
}