package com.misw.vinilos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.misw.vinilos.data.local.entities.ArtistEntity
import com.misw.vinilos.data.local.entities.ArtistWithAlbums

@Dao
interface ArtistDao {
    @Transaction
    @Query("SELECT * FROM artists")
    suspend fun getAllArtists(): List<ArtistWithAlbums>

    @Transaction
    @Query("SELECT * FROM artists WHERE id =:id")
    suspend fun getArtistById(id : Int): ArtistWithAlbums?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: ArtistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)
}