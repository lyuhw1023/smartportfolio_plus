package com.hyen.smartportfolio_plus.data.project

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "projects")
data class Project (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val detailLink: String,
    val timestamp: Long = System.currentTimeMillis()
)