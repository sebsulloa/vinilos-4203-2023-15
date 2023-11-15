package com.misw.vinilos.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.repository.AlbumRepository
import com.skydoves.sandwich.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    val albums = mutableStateOf<List<Album>>(emptyList())
    val isLoading = mutableStateOf(false)
    val hasError = mutableStateOf(false)

    private val _selectedAlbum = mutableStateOf<Album?>(null)
    val selectedAlbum: State<Album?> get() = _selectedAlbum

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                albums.value = repository.getAlbums().getOrThrow()
                hasError.value = false
            } catch (e: Exception) {
                hasError.value = true
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onAlbumSelected(album: Album) {
        _selectedAlbum.value = album
    }
}