package com.smi.giphy.ui

sealed class GifUiState<T> {
    class Loading<T> : GifUiState<T>()
    class Success<T>(val success: T) : GifUiState<T>()
    class Failed<T>(val error: String) : GifUiState<T>()
    class Empty<T> : GifUiState<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(success: T) = Success(success = success)
        fun <T> failed(error: String) = Failed<T>(error = error)
        fun <T> empty() = Empty<T>()
    }
}