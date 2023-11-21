package com.misw.vinilos.data.repository

import com.misw.vinilos.data.remote.models.Collector
import com.misw.vinilos.data.remote.services.CollectorService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class CollectorRepository @Inject constructor(
    private val collectorService: CollectorService
) {
    suspend fun getCollectors(): ApiResponse<List<Collector>> {
        return collectorService.getArtists()
    }

    suspend fun getCollectorDetail(collectorId: Int): ApiResponse<Collector> {
        return collectorService.getCollectorDetail(collectorId)
    }
}