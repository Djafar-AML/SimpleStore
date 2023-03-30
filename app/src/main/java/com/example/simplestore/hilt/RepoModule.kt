package com.example.simplestore.hilt

import com.example.simplestore.network.ApiClient
import com.example.simplestore.ui.fragments.productslistfragment.repo.SharedRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepo(apiClient: ApiClient): SharedRepo = SharedRepo(apiClient)

}