package com.example.foody.di

import com.example.foody.network.FoodyService
import com.example.foody.persistence.FoodyDao
import com.example.foody.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        foodyService: FoodyService,
        foodyDao: FoodyDao
    ): MainRepository {
        return MainRepository(foodyService, foodyDao)
    }
}