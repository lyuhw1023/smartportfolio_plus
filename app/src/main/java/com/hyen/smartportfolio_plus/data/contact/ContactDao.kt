package com.hyen.smartportfolio_plus.data.contact

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// DB 작업
@Dao
interface ContactDao {
    // 프로젝트 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    // 프로젝트 수정
    @Update
    suspend fun update(contact: Contact)

    // 프로젝트 삭제
    @Delete
    suspend fun delete(contact: Contact)

    // 전체 프로젝트 조회
    @Query("SELECT * FROM contacts ORDER BY timestamp DESC")
    fun getAllContacts(): Flow<List<Contact>>
}