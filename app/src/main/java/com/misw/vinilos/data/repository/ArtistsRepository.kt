package com.misw.vinilos.data.repository

import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.services.ArtistService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistService: ArtistService
) {
    suspend fun getArtists(): ApiResponse<List<Artist>> {
        return artistService.getArtists()
    }
}