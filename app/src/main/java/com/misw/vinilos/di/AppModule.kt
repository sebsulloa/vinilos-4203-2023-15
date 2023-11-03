package com.misw.vinilos.di

import com.misw.vinilos.data.remote.services.AlbumService
import com.misw.vinilos.data.repository.AlbumRepository
import com.misw.vinilos.data.remote.services.ArtistService
import com.misw.vinilos.data.repository.ArtistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAlbumRepository(albumService: AlbumService): AlbumRepository {
        return AlbumRepository(albumService)
    }

    @Provides
    @Singleton
    fun provideArtistRepository(artistService: ArtistService): ArtistRepository {
        return ArtistRepository(artistService)
    }
}

