package com.misw.vinilos.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.models.Collector

@Entity(tableName = "collector")
data class CollectorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
)

@Entity(tableName = "collector_artists", primaryKeys = ["collectorId", "artistId"])
data class CollectorArtistCrossRef(
    val collectorId: Int,
    val artistId: Int
)

data class CollectorWithArtists(
    @Embedded val collector: CollectorEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CollectorArtistCrossRef::class, parentColumn = "collectorId", entityColumn = "artistId")
    )
    val artists: List<ArtistEntity>
)
{
    fun toCollector(): Collector {
        return Collector(
            id = collector.id,
            name = collector.name,
            telephone = collector.telephone,
            email = collector.email,
            favoritePerformers = artists.map  {
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

