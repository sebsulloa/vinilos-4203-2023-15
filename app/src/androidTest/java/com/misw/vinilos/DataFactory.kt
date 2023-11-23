package com.misw.vinilos

import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.models.Collector
import com.misw.vinilos.data.remote.models.TrackForm
import io.github.serpro69.kfaker.Faker
import java.time.format.DateTimeFormatter

object DataFactory {
    private val faker = Faker()
    fun createAlbum(): Album {
        return Album(
            id = faker.random.nextInt(intRange = 0..1000),
            name = faker.music.albums(),
            cover = faker.random.randomString(length = 15),
            releaseDate = faker.random.randomString(length = 15),
            description = faker.random.randomString(length = 15),
            genre = faker.music.genres(),
            recordLabel = faker.company.name(),
            tracks = emptyList(),
            performers = emptyList()
        )
    }

    fun createArtist(): Artist {
        return Artist(
            id = faker.random.nextInt(intRange = 0..1000),
            name = faker.music.bands(),
            birthDate = faker.person.birthDate(30).format(DateTimeFormatter.ISO_DATE),
            image = faker.random.randomString(length = 15),
            description =faker.random.randomString(length = 15),
            albums = (1..5).map { createAlbum() }
        )
    }
    fun createCollector(): Collector {
        return Collector(
            id = faker.random.nextInt(intRange = 0..1000),
            name = faker.name.name(),
            telephone = faker.phoneNumber.phoneNumber(),
            email = faker.internet.email(),
            favoritePerformers = emptyList()
        )
    }

    fun createAlbumForm(): Album {
        val recordLabels = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
        val genres = listOf("Classical", "Salsa", "Rock", "Folk")

        return Album(
            id = faker.random.nextInt(intRange = 0..1000),
            name = faker.music.albums(),
            cover = "https://source.unsplash.com/random/300x300/?music",
            releaseDate = faker.random.randomString(length = 15),
            description = faker.random.randomString(length = 15),
            genre = genres.random(),
            recordLabel = recordLabels.random(),
            tracks = emptyList(),
            performers = emptyList()
        )
    }

    fun createTrackForm(): TrackForm {
        return TrackForm(
            name = faker.music.albums(),
            minutes = faker.random.nextInt(0, 60),
            seconds = faker.random.nextInt(0, 60)
        )
    }
}