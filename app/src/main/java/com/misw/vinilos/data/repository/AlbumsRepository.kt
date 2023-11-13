package com.misw.vinilos.data.repository

import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.AlbumCreateRequest
import com.misw.vinilos.data.remote.services.AlbumService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService
) {
    suspend fun getAlbums(): ApiResponse<List<Album>> {
        return albumService.getAlbums()
    }

    suspend fun createAlbum(albumCreateRequest: AlbumCreateRequest): ApiResponse<Album> {
        return albumService.createAlbum(albumCreateRequest)
    }
}