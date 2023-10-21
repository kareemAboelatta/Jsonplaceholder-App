package com.example.androidtaskbosta.presentaion.ui.album_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.domain.usecases.FetchPhotosByAlbumIdUseCase
import com.example.common.utils.UIState
import com.example.common.utils.mapToCustomError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val fetchPhotosByAlbumIdUseCase: FetchPhotosByAlbumIdUseCase
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchText.asStateFlow()

    private val _albumPhotosState = MutableStateFlow<UIState<List<Photo>>>(UIState.Empty)
    val albumPhotosState: StateFlow<UIState<List<Photo>>> get() = _albumPhotosState.asStateFlow()

    val filteredPhotos = _searchText
        .debounce(500L)// simulate heavy requests we don't need to send a request until user stop typing
        .combine(_albumPhotosState) { query, state ->
            if (state is UIState.Success) {
                if (query.isBlank()) {
                    state.data
                } else {
                    state.data.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                }
            } else emptyList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun fetchAlbumDetails(albumId: Int) {
        viewModelScope.launch {
            _albumPhotosState.value = UIState.Loading
            runCatching {
                fetchPhotosByAlbumIdUseCase.execute(albumId)
            }.onSuccess { photos ->
                _albumPhotosState.value = if (photos.isEmpty()) {
                    UIState.Empty
                } else {
                    UIState.Success(photos)
                }
            }.onFailure { e ->
                _albumPhotosState.value = UIState.Error(mapToCustomError(e))
            }
        }
    }

    fun updateSearchQuery(text: String) {
        _searchText.value = text
    }
}
