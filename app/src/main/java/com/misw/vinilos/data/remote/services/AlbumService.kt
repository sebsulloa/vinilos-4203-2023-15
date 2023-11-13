package com.misw.vinilos.data.remote.services

import com.misw.vinilos.data.remote.models.Album
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface AlbumService {
    @GET("/albums")
    suspend fun getAlbums(): ApiResponse<List<Album>>
}