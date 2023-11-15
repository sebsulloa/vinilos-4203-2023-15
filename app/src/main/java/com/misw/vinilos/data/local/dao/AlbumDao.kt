package com.misw.vinilos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.misw.vinilos.data.local.entities.AlbumArtistCrossRef
import com.misw.vinilos.data.local.entities.AlbumEntity
import com.misw.vinilos.data.local.entities.AlbumWithRelations
import com.misw.vinilos.data.local.entities.ArtistEntity
import com.misw.vinilos.data.local.entities.TrackEntity
import com.misw.vinilos.data.remote.models.Album

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity)

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumWithRelations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumArtist(albumArtist: AlbumArtistCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumArtists(albumArtists: List<AlbumArtistCrossRef>)
}