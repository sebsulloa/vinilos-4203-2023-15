package com.misw.vinilos.data.remote.services

import com.misw.vinilos.data.remote.models.Artist
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ArtistService {
    @GET("/musicians")
    suspend fun getArtists(): ApiResponse<List<Artist>>
}