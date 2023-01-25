package com.smi.giphy.di

import android.content.Context
import androidx.room.Room
import com.smi.giphy.data.cache.GifDatabase
import com.smi.giphy.data.cache.LocalDataSource
import com.smi.giphy.data.remote.GiphyApi
import com.smi.giphy.data.remote.RemoteDataSource
import com.smi.giphy.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesGiphyApi(
        baseUrl: String, gsonConverterFactory: GsonConverterFactory
    ): GiphyApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(GiphyApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(api: GiphyApi): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun providesRoomDB(@ApplicationContext appContext : Context): GifDatabase {
        return Room.databaseBuilder(
            appContext,
            GifDatabase::class.java, "gif-database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(gifDatabase: GifDatabase): LocalDataSource {
        return LocalDataSource(gifDatabase)
    }

}