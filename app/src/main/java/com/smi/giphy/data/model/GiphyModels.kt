package com.smi.giphy.data.model

import com.google.gson.annotations.SerializedName

/**
 * Giphy API response models
 */
data class GifResponse(
    @SerializedName("data") val listOfGifs: List<Gif>,
    @SerializedName("status") val httpStatus: ResponseStatus
)

data class ResponseStatus (
    @SerializedName("status") val status: Int
)

/**
 * Giphy Data models
 */
data class Gif(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: ImageDetail,
)

data class ImageDetail(
    @SerializedName("downsized_medium") val downsized_small: Downsized
)

data class Downsized(
    @SerializedName("url") val url: String
)
