package com.hyen.smartportfolio_plus.viewmodel

import androidx.lifecycle.*
import com.hyen.smartportfolio_plus.data.firestore.FireStoreProjectRepository
import com.hyen.smartportfolio_plus.data.project.Project
import com.hyen.smartportfolio_plus.data.repository.ProjectRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProjectViewModel(
    private val roomRepo: ProjectRepository,
    private val cloudRepo: FireStoreProjectRepository
) : ViewModel() {

    val localProject: LiveData<List<Project>> = roomRepo.allProjects.asLiveData()
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        // 앱 시작 시, 아직 Firestore 에 동기화되지 않은 로컬 항목만 가져와서 동기화
        viewModelScope.launch {
            val unsynced = roomRepo.allProjects.first()
                .filter { it.firestoreId == null }
            unsynced.forEach { project ->
                cloudRepo.insert(project, { firestoreId ->
                    // 동기화 성공 시, local DB 에 firestoreId 업데이트
                    viewModelScope.launch {
                        roomRepo.update(project.copy(firestoreId = firestoreId))
                    }
                }, { ex ->
                    _error.postValue("Firestore insert 오류: ${ex.message}")
                })
            }
        }
    }

    fun insertLocal(project: Project) = viewModelScope.launch {
        // 로컬에 먼저 저장
        roomRepo.insert(project)
        // 방금 저장된 항목
        val inserted = roomRepo.allProjects.first()
            .find { it.timestamp == project.timestamp && it.firestoreId == null }
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

    fun updateLocal(project: Project) = viewModelScope.launch {
        // 로컬에 먼저 업데이트
        roomRepo.update(project)
        // 이미 firestoreId 있으면 cloud 갱신
        project.firestoreId?.let { id ->
            cloudRepo.update(project, {}, { ex ->
                _error.postValue("Firestore update 오류: ${ex.message}")
            })
        }
    }

    fun deleteLocal(project: Project) = viewModelScope.launch {
        // cloud 먼저 삭제
        project.firestoreId?.let { id ->
            cloudRepo.delete(id, {}, { ex ->
                _error.postValue("Firestore delete 오류: ${ex.message}")
            })
        }
        // 이후 로컬 삭제
        roomRepo.delete(project)
    }
}