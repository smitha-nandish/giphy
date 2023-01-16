package com.smi.giphy.data.repository

import com.smi.giphy.data.cache.LocalDataSource
import com.smi.giphy.data.model.Downsized
import com.smi.giphy.data.model.Gif
import com.smi.giphy.data.model.ImageDetail
import com.smi.giphy.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GiphyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    ): IGiphyRepository {

    override suspend fun getTrendingGifs(): Flow<List<Gif>> =
        remoteDataSource.getTrendingGifsFromRemote()

    override suspend fun getGifsForSearch(key: String): Flow<List<Gif>> =
        remoteDataSource.getGifsForSearch(key)

    override suspend fun getFavouriteGifs(): Flow<List<Gif>> =
        localDataSource.getFavouriteGifs()
            .map { gifEntities ->
                gifEntities.map {gifEntity ->
                    val images = ImageDetail(downsized_small = Downsized(gifEntity.url))
                     Gif(gifEntity.id, gifEntity.title, images)
                }
            }

    override suspend fun saveGif(item: Gif): Flow<List<Gif>> =
        localDataSource.saveGifAndRetrieve(item)
            .map { gifEntities ->
                gifEntities.map {gifEntity ->
                    val images = ImageDetail(downsized_small = Downsized(gifEntity.url))
                    Gif(gifEntity.id, gifEntity.title, images)
                }
            }

    override suspend fun removeGif(item: Gif): Flow<List<Gif>> =
        localDataSource.removeGifAndRetrieve(item)
            .map { gifEntities ->
                gifEntities.map {gifEntity ->
                    val images = ImageDetail(downsized_small = Downsized(gifEntity.url))
                    Gif(gifEntity.id, gifEntity.title, images)
                }
            }

}