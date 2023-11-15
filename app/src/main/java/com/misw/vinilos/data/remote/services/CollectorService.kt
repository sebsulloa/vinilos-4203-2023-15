package com.misw.vinilos.data.remote.services

import com.misw.vinilos.data.remote.models.Collector
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface CollectorService {
    @GET("/collectors")
    suspend fun getArtists(): ApiResponse<List<Collector>>
}