package com.smi.giphy.data.cache

import com.smi.giphy.data.model.Gif
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val gifDatabase: GifDatabase
) {
    fun getFavouriteGifs(): Flow<List<GifEntity>> =
        flow {
            emit(gifDatabase.gifDao().getSavedGifs())
        }

    fun saveGifAndRetrieve(gif: Gif): Flow<List<GifEntity>> =
        flow {
            gifDatabase.gifDao().insertAll(GifEntity(id = gif.id, title = gif.title, url = gif.images.downsized_small.url))
            emit(gifDatabase.gifDao().getSavedGifs())
        }

    fun removeGifAndRetrieve(gif: Gif): Flow<List<GifEntity>> =
        flow {
            gifDatabase.gifDao().delete(GifEntity(id = gif.id, title = gif.title, url = gif.images.downsized_small.url))
            emit(gifDatabase.gifDao().getSavedGifs())
        }
}