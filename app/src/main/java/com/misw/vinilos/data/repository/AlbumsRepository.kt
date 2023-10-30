package com.misw.vinilos.data.repository

import com.misw.vinilos.data.remote.services.AlbumService
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService
) {
    suspend fun getAlbums() = albumService.getAlbums()
}