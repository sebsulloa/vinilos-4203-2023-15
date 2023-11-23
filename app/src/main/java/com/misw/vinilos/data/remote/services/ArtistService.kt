package com.misw.vinilos.data.remote.services

import com.misw.vinilos.data.remote.models.Artist
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistService {
    @GET("/musicians")
    suspend fun getArtists(): ApiResponse<List<Artist>>

    @GET("/musicians/{artistId}")
    suspend fun getArtistDetail(@Path("artistId") artistId: Int): ApiResponse<Artist>
}