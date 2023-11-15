package com.misw.vinilos.data.repository

import android.util.Log
import com.misw.vinilos.data.local.dao.AlbumDao
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.AlbumCreateRequest
import com.misw.vinilos.data.remote.services.AlbumService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService,
    private val albumDao: AlbumDao
) {
    suspend fun getAlbums(): ApiResponse<List<Album>> {
        val localAlbums = albumDao.getAllAlbums()
        if (localAlbums.isNotEmpty()) {
            return ApiResponse.Success(localAlbums.map { it.toAlbum() })
        }

        val remoteResponse = albumService.getAlbums()

        if (remoteResponse is ApiResponse.Success) {
            val remoteAlbums = remoteResponse.data
            remoteAlbums.forEach { albumDao.insertAlbum(it.toAlbumEntity()) }
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
}