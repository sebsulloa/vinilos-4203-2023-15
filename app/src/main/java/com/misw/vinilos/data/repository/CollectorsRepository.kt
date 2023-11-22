package com.misw.vinilos.data.repository

import com.misw.vinilos.data.local.dao.ArtistDao
import com.misw.vinilos.data.local.dao.CollectorDao
import com.misw.vinilos.data.local.entities.ArtistEntity
import com.misw.vinilos.data.local.entities.CollectorArtistCrossRef
import com.misw.vinilos.data.remote.models.Collector
import com.misw.vinilos.data.remote.services.CollectorService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class CollectorRepository @Inject constructor(
    private val collectorService: CollectorService,
    private val artistDao: ArtistDao,
    private val collectorDao: CollectorDao
) {
    suspend fun getCollectors(): ApiResponse<List<Collector>> {
        val localCollectors = collectorDao.getAllCollectors()
        if (localCollectors.isNotEmpty()) {
            return ApiResponse.Success(localCollectors.map { it.toCollector() })
        }

        val remoteResponse = collectorService.getCollectors()

        if (remoteResponse is ApiResponse.Success) {
            val remoteCollectors = remoteResponse.data

            // Batch insert collectors
            val collectorEntities = remoteCollectors.map { it.toCollectorEntity() }
            collectorDao.insertCollectors(collectorEntities)

            // Batch insert artists and album artists
            val artistEntities = remoteCollectors.flatMap { collector ->
                collector.favoritePerformers.map {
                    ArtistEntity(
                        it.id,
                        it.name,
                        it.image,
                        it.description,
                        it.birthDate
                    )
                }
            }
            val collectorArtistCrossRefs = remoteCollectors.flatMap { collector ->
                collector.favoritePerformers.map { CollectorArtistCrossRef(collector.id, it.id) }
            }

            artistDao.insertArtists(artistEntities)
            collectorDao.insertCollectorArtists(collectorArtistCrossRefs)
        }

        return remoteResponse
    }

    suspend fun getCollectorDetail(collectorId: Int): ApiResponse<Collector> {

        val localCollector = collectorDao.getCollectorById(collectorId)

        if (localCollector != null) {
            return ApiResponse.Success(localCollector.toCollector())
        }

        val remoteResponse = collectorService.getCollectorDetail(collectorId)

        if (remoteResponse is ApiResponse.Success) {
            val remoteCollector = remoteResponse.data

            //  Insert collector
            collectorDao.insertCollector(remoteCollector.toCollectorEntity())

            // Batch insert artists and album artists
            val artistEntities = remoteCollector.favoritePerformers.map {
                ArtistEntity(
                    it.id,
                    it.name,
                    it.image,
                    it.description,
                    it.birthDate
                )
            }

            val collectorArtistCrossRefs = remoteCollector.favoritePerformers.map {
                CollectorArtistCrossRef(
                    remoteCollector.id,
                    it.id
                )
            }

            artistDao.insertArtists(artistEntities)
            collectorDao.insertCollectorArtists(collectorArtistCrossRefs)
        }

        return remoteResponse

    }
}