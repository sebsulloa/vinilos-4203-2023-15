package com.misw.vinilos.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.repository.ArtistRepository
import com.skydoves.sandwich.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val repository: ArtistRepository
) : ViewModel() {

    val artists = mutableStateOf<List<Artist>>(emptyList())
    val isLoading = mutableStateOf(false)
    val hasError = mutableStateOf(false)

    init {
        fetchArtists()
    }

    private fun fetchArtists() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                artists.value = repository.getArtists().getOrThrow()
                hasError.value = false
            } catch (e: Exception) {
                hasError.value = true
            } finally {
                isLoading.value = false
            }
        }
    }
}