package com.misw.vinilos

import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Artist
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
}