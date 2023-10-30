package com.misw.vinilos.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.repository.AlbumRepository
import com.skydoves.sandwich.getOrElse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    val albums = mutableStateOf<List<Album>>(emptyList())
    val isLoading = mutableStateOf(false)


    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            isLoading.value = true
            albums.value = repository.getAlbums().getOrElse(emptyList())
            isLoading.value = false
        }
    }
}