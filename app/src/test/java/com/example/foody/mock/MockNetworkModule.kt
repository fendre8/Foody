package com.example.foody.mock

import com.example.foody.network.FoodyService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockNetworkModule {
    @Provides
    @Singleton
    fun provideShowService(): FoodyService = MockFoodyService()
}