package com.misw.vinilos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.misw.vinilos.data.remote.models.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String
) {
    fun toAlbum(): Album {
        return Album(
            id = id,
            name = name,
            cover = cover,
            releaseDate = releaseDate,
            description = description,
            genre = genre,
            recordLabel = recordLabel,
            tracks = emptyList(),
            performers = emptyList()
        )
    }

    companion object {
        fun fromAlbum(album: Album): AlbumEntity {
            return AlbumEntity(
                id = album.id,
                name = album.name,
                cover = album.cover,
                releaseDate = album.releaseDate,
                description = album.description,
                genre = album.genre,
                recordLabel = album.recordLabel
            )
        }
    }
}
