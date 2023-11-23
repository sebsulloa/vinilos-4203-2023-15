package com.misw.vinilos.data.repository

import com.misw.vinilos.data.local.dao.AlbumDao
import com.misw.vinilos.data.local.dao.ArtistDao
import com.misw.vinilos.data.local.entities.AlbumArtistCrossRef
import com.misw.vinilos.data.local.entities.AlbumEntity
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.services.ArtistService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistService: ArtistService,
    private val artistDao: ArtistDao,
    private val albumDao: AlbumDao
) {
    suspend fun getArtists(): ApiResponse<List<Artist>> {
        val localArtists = artistDao.getAllArtists()
        if (localArtists.isNotEmpty()) {
            val allBirthDatesEmpty = localArtists.all { it.artist.birthDate.isNullOrEmpty() }
            if (!allBirthDatesEmpty) {
                return ApiResponse.Success(localArtists.map { it.toArtist() })
            }
        }

        val remoteResponse = artistService.getArtists()
        if (remoteResponse is ApiResponse.Success) {
            val remoteArtists = remoteResponse.data

            // Batch insert artists
            val artistEntities = remoteArtists.map { it.toArtistEntity() }
            artistDao.insertArtists(artistEntities)

            // Batch insert artists and album artists
            val albumEntities = remoteArtists.flatMap { artist ->
                artist.albums.map {
                    AlbumEntity(
                        it.id,
                        it.name,
                        it.cover,
                        it.releaseDate,
                        it.description,
                        it.genre,
                        it.recordLabel
                    )
                }
            }
            val albumArtistCrossRefs = remoteArtists.flatMap { artist ->
                artist.albums.map { AlbumArtistCrossRef(it.id, artist.id) }
            }

            albumDao.insertAlbums(albumEntities)
            albumDao.insertAlbumArtists(albumArtistCrossRefs)
        }

        return remoteResponse
    }

    suspend fun getArtistDetail(artistId: Int): ApiResponse<Artist> {
        val localArtist = artistDao.getArtistById(artistId)
        if (localArtist != null) {
            return ApiResponse.Success(localArtist.toArtist())
        }

        val remoteResponse = artistService.getArtistDetail(artistId)
        if (remoteResponse is ApiResponse.Success) {
            val remoteArtist = remoteResponse.data

            // Insert artists
            artistDao.insertArtist(remoteArtist.toArtistEntity())

            // Batch insert artists and album artists
            val albumEntities = remoteArtist.albums.map {
                AlbumEntity(
                    it.id,
                    it.name,
                    it.cover,
                    it.releaseDate,
                    it.description,
                    it.genre,
                    it.recordLabel
                )
            }

            val albumArtistCrossRefs =
                remoteArtist.albums.map { AlbumArtistCrossRef(it.id, remoteArtist.id) }

            albumDao.insertAlbums(albumEntities)
            albumDao.insertAlbumArtists(albumArtistCrossRefs)
        }

        return remoteResponse
    }

    suspend fun getArtistDetail(artistId: Int): ApiResponse<Artist> {
        return artistService.getArtistDetail(artistId)
    }
}