package com.misw.vinilos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.misw.vinilos.data.local.entities.CollectorArtistCrossRef
import com.misw.vinilos.data.local.entities.CollectorEntity
import com.misw.vinilos.data.local.entities.CollectorWithArtists

@Dao
interface CollectorDao {
    @Transaction
    @Query("SELECT * FROM collector")
    suspend fun getAllCollectors(): List<CollectorWithArtists>

    @Transaction
    @Query("SELECT * FROM collector WHERE id =:id")
    suspend fun getCollectorById(id : Int): CollectorWithArtists?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectorArtist(collectorArtist: CollectorArtistCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectors(collectors: List<CollectorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectorArtists(collectorArtists: List<CollectorArtistCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollector(collector: CollectorEntity)
}