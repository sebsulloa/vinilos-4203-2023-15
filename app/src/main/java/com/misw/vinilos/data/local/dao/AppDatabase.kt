package com.misw.vinilos.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.misw.vinilos.data.local.entities.AlbumArtistCrossRef
import com.misw.vinilos.data.local.entities.AlbumEntity
import com.misw.vinilos.data.local.entities.ArtistEntity
import com.misw.vinilos.data.local.entities.CollectorArtistCrossRef
import com.misw.vinilos.data.local.entities.CollectorEntity
import com.misw.vinilos.data.local.entities.TrackEntity

@Database(entities = [AlbumEntity::class, ArtistEntity::class, TrackEntity::class, AlbumArtistCrossRef::class, CollectorEntity::class, CollectorArtistCrossRef::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    abstract fun artistDao(): ArtistDao

    abstract fun trackDao(): TrackDao

    abstract fun collectorDao(): CollectorDao
}