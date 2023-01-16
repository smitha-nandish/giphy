package com.smi.giphy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smi.giphy.data.model.Gif
import com.smi.giphy.data.repository.GiphyRepositoryImpl
import com.smi.giphy.data.repository.IGiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val giphyRepository: GiphyRepositoryImpl
): ViewModel() {

    private var fetchJob : Job? = null

    private val _gifUiState = MutableStateFlow<GifUiState<List<Gif>>>(GifUiState.empty())
    val gifUiState: StateFlow<GifUiState<List<Gif>>> = _gifUiState

    private val _gifSavedUiState = MutableStateFlow<GifUiState<List<Gif>>>(GifUiState.empty())
    val gifSavedUiState: StateFlow<GifUiState<List<Gif>>> = _gifSavedUiState

    fun fetchTrendingGifs() {
        _gifUiState.value = GifUiState.loading()
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            giphyRepository.getTrendingGifs()
                .catch { _gifUiState.value = GifUiState.failed(it.localizedMessage) }
                .collect { gifs -> _gifUiState.value = GifUiState.success(gifs) }
        }
    }

    fun searchFor(query: String) {
        _gifUiState.value = GifUiState.loading()
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            giphyRepository.getGifsForSearch(query)
                .catch { _gifUiState.value = GifUiState.failed(it.localizedMessage) }
                .collect { gifs -> _gifUiState.value = GifUiState.success(gifs) }
        }
    }

    fun fetchFavouriteGifs() {
        _gifSavedUiState.value = GifUiState.loading()
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            giphyRepository.getFavouriteGifs()
                .flowOn(Dispatchers.IO)
                .catch { _gifSavedUiState.value = GifUiState.failed(it.localizedMessage) }
                .collect { gifs -> _gifSavedUiState.value = GifUiState.success(gifs) }
        }
    }

    fun saveGifToFavourite(item: Gif) {
        _gifSavedUiState.value = GifUiState.loading()
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            giphyRepository.saveGif(item)
                .flowOn(Dispatchers.IO)
                .catch { _gifSavedUiState.value = GifUiState.failed(it.localizedMessage) }
                .collect { gifs -> _gifSavedUiState.value = GifUiState.success(gifs) }

        }
    }

    fun removeGifFromFavourite(item: Gif) {
        _gifSavedUiState.value = GifUiState.loading()
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            giphyRepository.removeGif(item)
                .flowOn(Dispatchers.IO)
                .catch { _gifSavedUiState.value = GifUiState.failed(it.localizedMessage) }
                .collect { gifs -> _gifSavedUiState.value = GifUiState.success(gifs) }

        }
    }

}
