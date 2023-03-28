package com.example.simplestore.hilt

import com.example.simplestore.redux.state.ApplicationState
import com.example.simplestore.redux.store.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStoreModule {

    @Provides
    @Singleton
    fun provideApplicationStateStore(): Store<ApplicationState> {
        return Store(ApplicationState())
    }

}