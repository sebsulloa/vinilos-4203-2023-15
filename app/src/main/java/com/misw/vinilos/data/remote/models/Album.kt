package com.misw.vinilos.data.remote.models

import com.misw.vinilos.data.local.entities.AlbumEntity

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track>,
    val performers: List<Artist>
) {
    fun toAlbumEntity(): AlbumEntity {
        return AlbumEntity(
            id = id,
            name = name,
            cover = cover,
            releaseDate = releaseDate,
            description = description,
            genre = genre,
            recordLabel = recordLabel
        )
    }
}