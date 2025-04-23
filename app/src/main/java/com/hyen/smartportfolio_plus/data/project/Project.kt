package com.hyen.smartportfolio_plus.data.project

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "projects")
data class Project (
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val firestoreId: String? = null,
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val detailLink: String = "",
    val timestamp: Long = System.currentTimeMillis()
)