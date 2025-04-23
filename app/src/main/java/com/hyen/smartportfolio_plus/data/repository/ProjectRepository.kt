package com.hyen.smartportfolio_plus.data.repository

import com.hyen.smartportfolio_plus.data.project.Project
import com.hyen.smartportfolio_plus.data.project.ProjectDao
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    // 전체 프로젝트 목록
    val allProjects: Flow<List<Project>> = projectDao.getAllProjects()

    // 프로젝트 추가
    suspend fun insert(project: Project) {
        projectDao.insert(project)
    }
    // 프로젝트 수정
    suspend fun update(project: Project) {
        projectDao.update(project)
    }
    // 프로젝트 삭제
    suspend fun delete(project: Project) {
        projectDao.delete(project)
    }
}