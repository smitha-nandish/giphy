package com.smi.giphy.data.remote

import com.smi.giphy.data.model.Gif
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val giphyApi : GiphyApi
    ) {

    suspend fun getTrendingGifsFromRemote(): Flow<List<Gif>> =
        flow {
            val trendingGifs = giphyApi.fetchTrendingGifs()
            emit(trendingGifs.listOfGifs)
            delay(5000)
        }

    suspend fun getGifsForSearch(search: String): Flow<List<Gif>> =
        flow {
            val trendingGifsForSearch = giphyApi.getGifsForSearch(searchKey = search)
            emit(trendingGifsForSearch.listOfGifs)
            delay(5000)
        }
}

