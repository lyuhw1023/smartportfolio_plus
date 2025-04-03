package com.hyen.smartportfolio_plus.data.contact

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val name: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)