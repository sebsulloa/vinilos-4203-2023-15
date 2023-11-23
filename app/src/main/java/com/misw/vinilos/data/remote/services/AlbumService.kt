package com.misw.vinilos.data.remote.services

import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.AlbumCreateRequest
import com.misw.vinilos.data.remote.models.Track
import com.misw.vinilos.data.remote.models.TrackCreateRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumService {
    @GET("/albums")
    suspend fun getAlbums(): ApiResponse<List<Album>>

    @POST("/albums")
    suspend fun createAlbum(@Body albumCreateRequest: AlbumCreateRequest): ApiResponse<Album>

    @POST("/albums/{albumId}/tracks")
    suspend fun createTrack(
        @Path("albumId") albumId: Int,
        @Body trackCreateRequest: TrackCreateRequest
    ): ApiResponse<Track>
}