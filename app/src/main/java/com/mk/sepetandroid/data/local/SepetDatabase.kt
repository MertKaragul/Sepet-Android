package com.mk.sepetandroid.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk.sepetandroid.data.local.entity.TokenEntity
import com.mk.sepetandroid.data.local.local_interface.TokenDao

@Database(
    entities = [
        TokenEntity::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class SepetDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}