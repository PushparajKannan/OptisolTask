package com.example.optisoltask.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.optisoltask.database.dao.RoomDao
import com.example.optisoltask.database.tablemodel.RoomsDbModel

@Database(
    entities = [RoomsDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun roomsDao(): RoomDao

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "OptiTask"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return sInstance
        }
    }
}