package com.misw.vinilos.data.remote.services

import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.AlbumCreateRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumService {
    @GET("/albums")
    suspend fun getAlbums(): ApiResponse<List<Album>>

    @POST("/albums")
    suspend fun createAlbum(@Body albumCreateRequest: AlbumCreateRequest): ApiResponse<Album>
}