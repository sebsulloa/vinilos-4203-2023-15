package com.misw.vinilos

import com.misw.vinilos.data.remote.models.Album
import io.github.serpro69.kfaker.Faker

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
}