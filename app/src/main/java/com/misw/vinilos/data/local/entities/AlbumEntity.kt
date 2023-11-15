package com.misw.vinilos.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.models.Track

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
)

@Entity(tableName = "album_artists", primaryKeys = ["albumId", "artistId"])
data class AlbumArtistCrossRef(
    val albumId: Int,
    val artistId: Int
)

data class AlbumWithRelations(
    @Embedded val album: AlbumEntity,
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val tracks: List<TrackEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(AlbumArtistCrossRef::class, parentColumn = "albumId", entityColumn = "artistId")
    )
    val artists: List<ArtistEntity>
) {
    fun toAlbum(): Album {
        return Album(
            id = album.id,
            name = album.name,
            cover = album.cover,
            releaseDate = album.releaseDate,
            description = album.description,
            genre = album.genre,
            recordLabel = album.recordLabel,
            tracks = tracks.map { Track(it.id, it.name, it.duration) },
            performers = artists.map {
                Artist(
                    it.id,
                    it.name,
                    it.image,
                    it.description,
                    it.birthDate +"",
                    emptyList()
                )
            }
        )
    }
}
