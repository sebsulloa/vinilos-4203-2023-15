package com.misw.vinilos.data.remote.services

import com.misw.vinilos.data.remote.models.Collector
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorService {
    @GET("/collectors")
    suspend fun getCollectors(): ApiResponse<List<Collector>>

    @GET("/collectors/{collectorId}")
    suspend fun getCollectorDetail(@Path("collectorId") collectorId: Int): ApiResponse<Collector>
}