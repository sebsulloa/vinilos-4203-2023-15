package com.misw.vinilos.data.repository

import com.misw.vinilos.data.remote.network.RetrofitInstance
import com.misw.vinilos.data.remote.services.AlbumService

class AlbumRepository {

    private val albumService: AlbumService = RetrofitInstance.retrofit.create(AlbumService::class.java)

    suspend fun getAlbums() = albumService.getAlbums()
}