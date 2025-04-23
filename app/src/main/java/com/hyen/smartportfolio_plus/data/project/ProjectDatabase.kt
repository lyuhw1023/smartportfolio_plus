package com.hyen.smartportfolio_plus.data.project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room 데이터베이스 설정
@Database(
    entities = [Project::class],
    version = 1,
    exportSchema = false
)
abstract class ProjectDatabase: RoomDatabase() {
    abstract fun projectDao(): ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getDatabase(context: Context): ProjectDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectDatabase::class.java,
                    "project_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}