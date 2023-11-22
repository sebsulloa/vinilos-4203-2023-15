package com.misw.vinilos.data.remote.models

import com.misw.vinilos.data.local.entities.ArtistEntity

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<Album>
) {
    fun toArtistEntity(): ArtistEntity {
        return ArtistEntity(id, name, image, description, birthDate)
    }
}