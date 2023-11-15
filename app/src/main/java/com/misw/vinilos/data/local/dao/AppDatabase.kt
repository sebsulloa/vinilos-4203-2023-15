package com.misw.vinilos.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.misw.vinilos.data.local.entities.AlbumEntity

@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}