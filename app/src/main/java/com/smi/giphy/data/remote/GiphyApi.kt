package com.smi.giphy.data.remote

import com.smi.giphy.data.model.GifResponse
import com.smi.giphy.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GiphyApi {

    /**
     * Trending Gifs API
     */
    @GET("trending?api_key=ez18Cr9sHAfJgjC7uJOD3OTzJSiOiI3k")
    suspend fun fetchTrendingGifs(
        @Query("limit") limit: Int = 30,
        @Query("rating") rating: String = "g"
    ) : GifResponse

    /**
     * Search Gifs API
     */
    @GET("search?api_key=ez18Cr9sHAfJgjC7uJOD3OTzJSiOiI3k")
    suspend fun getGifsForSearch(
        @Query("limit") limit: Int = 30,
        @Query("rating") rating: String = "g",
        @Query("q") searchKey: String = "",
    ) : GifResponse

}