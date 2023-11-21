package com.misw.vinilos.data.repository

import com.misw.vinilos.data.local.dao.AlbumDao
import com.misw.vinilos.data.local.dao.ArtistDao
import com.misw.vinilos.data.local.dao.TrackDao
import com.misw.vinilos.data.local.entities.AlbumArtistCrossRef
import com.misw.vinilos.data.local.entities.ArtistEntity
import com.misw.vinilos.data.local.entities.TrackEntity
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.AlbumCreateRequest
import com.misw.vinilos.data.remote.models.Track
import com.misw.vinilos.data.remote.models.TrackCreateRequest
import com.misw.vinilos.data.remote.services.AlbumService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService,
    private val albumDao: AlbumDao,
    private val trackDao: TrackDao,
    private val artistDao: ArtistDao

) {
    suspend fun getAlbums(): ApiResponse<List<Album>> {
        val localAlbums = albumDao.getAllAlbums()
        if (localAlbums.isNotEmpty()) {
            return ApiResponse.Success(localAlbums.map { it.toAlbum() })
        }

        val remoteResponse = albumService.getAlbums()

        if (remoteResponse is ApiResponse.Success) {
            val remoteAlbums = remoteResponse.data

            // Batch insert albums
            val albumEntities = remoteAlbums.map { it.toAlbumEntity() }
            albumDao.insertAlbums(albumEntities)

            // Batch insert tracks
            val trackEntities = remoteAlbums.flatMap { album ->
                album.tracks.map { TrackEntity(it.id, it.name, it.duration, album.id) }
            }
            trackDao.insertTracks(trackEntities)

            // Batch insert artists and album artists
            val artistEntities = remoteAlbums.flatMap { album ->
                album.performers.map { ArtistEntity(it.id, it.name, it.image, it.description, it.birthDate) }
            }
            val albumArtistCrossRefs = remoteAlbums.flatMap { album ->
                album.performers.map { AlbumArtistCrossRef(album.id, it.id) }
            }

            artistDao.insertArtists(artistEntities)
            albumDao.insertAlbumArtists(albumArtistCrossRefs)
        }

        return remoteResponse
    }

    suspend fun createAlbum(albumCreateRequest: AlbumCreateRequest): ApiResponse<Album> {
        val remoteResponse = albumService.createAlbum(albumCreateRequest)

        if (remoteResponse is ApiResponse.Success) {
            albumDao.insertAlbum(remoteResponse.data.toAlbumEntity())
        }

        return remoteResponse
    }

    suspend fun createTrack(albumId: Int, trackCreateRequest: TrackCreateRequest): ApiResponse<Track> {
        val remoteResponse = albumService.createTrack(albumId, trackCreateRequest)
        return remoteResponse
    }
}