package com.smi.giphy.di

import com.smi.giphy.data.repository.GiphyRepositoryImpl
import com.smi.giphy.data.repository.IGiphyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsGiphyRepository(
        giphyRepositoryImpl: GiphyRepositoryImpl
    ) : IGiphyRepository

}