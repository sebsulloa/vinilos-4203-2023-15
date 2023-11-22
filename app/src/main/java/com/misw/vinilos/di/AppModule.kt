package com.misw.vinilos.di

import android.content.Context
import androidx.room.Room
import com.misw.vinilos.data.local.dao.AlbumDao
import com.misw.vinilos.data.local.dao.AppDatabase
import com.misw.vinilos.data.local.dao.ArtistDao
import com.misw.vinilos.data.local.dao.CollectorDao
import com.misw.vinilos.data.local.dao.TrackDao
import com.misw.vinilos.data.remote.services.AlbumService
import com.misw.vinilos.data.remote.services.ArtistService
import com.misw.vinilos.data.remote.services.CollectorService
import com.misw.vinilos.data.repository.AlbumRepository
import com.misw.vinilos.data.repository.ArtistRepository
import com.misw.vinilos.data.repository.CollectorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAlbumRepository(albumService: AlbumService, albumDao: AlbumDao, trackDao: TrackDao, artistDao: ArtistDao): AlbumRepository {
        return AlbumRepository(albumService, albumDao, trackDao, artistDao)
    }

    @Provides
    @Singleton
    fun provideArtistRepository(artistService: ArtistService): ArtistRepository {
        return ArtistRepository(artistService)
    }

    @Provides
    @Singleton
    fun provideCollectorRepository(collectorService: CollectorService, artistDao: ArtistDao, collectorDao: CollectorDao): CollectorRepository {
        return CollectorRepository(collectorService, artistDao, collectorDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-database"
        ).
        fallbackToDestructiveMigration().
        build()
    }

    @Provides
    @Singleton
    fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.albumDao()
    }

    @Provides
    @Singleton
    fun provideArtistDao(appDatabase: AppDatabase): ArtistDao {
        return appDatabase.artistDao()
    }

    @Provides
    @Singleton
    fun provideTrackDao(appDatabase: AppDatabase): TrackDao {
        return appDatabase.trackDao()
    }

    @Provides
    @Singleton
    fun provideCollectorDao(appDatabase: AppDatabase): CollectorDao {
        return appDatabase.collectorDao()
    }
}

