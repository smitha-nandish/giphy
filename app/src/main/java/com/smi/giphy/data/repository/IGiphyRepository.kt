package com.smi.giphy.data.repository

import com.smi.giphy.data.model.Gif
import kotlinx.coroutines.flow.Flow

interface IGiphyRepository {
    suspend fun getTrendingGifs() : Flow<List<Gif>>
    suspend fun getGifsForSearch(key: String) : Flow<List<Gif>>
    suspend fun getFavouriteGifs() : Flow<List<Gif>>
    suspend fun saveGif(item: Gif) : Flow<List<Gif>>
    suspend fun removeGif(item: Gif) : Flow<List<Gif>>
}