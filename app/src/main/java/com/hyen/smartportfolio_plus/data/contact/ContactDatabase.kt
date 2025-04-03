package com.hyen.smartportfolio_plus.data.contact

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// viewmodel - repository - DAO - DB

// room 데이터베이스 설정 정의 추상 클래스
@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)

abstract class ContactDatabase : RoomDatabase() {
    abstract  fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        // db 인스턴스 반환
        fun getDatabase(context: Context): ContactDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}