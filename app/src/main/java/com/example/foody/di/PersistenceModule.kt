package com.example.foody.di

import android.app.Application
import androidx.room.Room
import com.example.foody.persistence.AppDatabase
import com.example.foody.persistence.FoodyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "foody-db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTopShowDao(appDatabase: AppDatabase): FoodyDao {
        return appDatabase.foodyDao()
    }

}