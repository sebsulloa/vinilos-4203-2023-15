package com.misw.vinilos.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Artist

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String?
)

data class ArtistWithAlbums(
    @Embedded val artist: ArtistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
        Junction(
            AlbumArtistCrossRef::class, parentColumn = "albumId", entityColumn = "artistId"
        )
    ) val albums: List<AlbumEntity>
) {
    fun toArtist(): Artist {
        return Artist(id = artist.id,
            name = artist.name,
            image = artist.image,
            description = artist.description,
            birthDate = artist.birthDate + "",
            albums = albums.map {
                Album(
                    it.id,
                    it.name,
                    it.cover,
                    it.releaseDate,
                    it.description,
                    it.genre,
                    it.recordLabel,
                    emptyList(),
                    emptyList(),
                )
            })
    }
}