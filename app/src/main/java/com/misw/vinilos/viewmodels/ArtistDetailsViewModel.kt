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
class ArtistDetailsViewModel @Inject constructor(
    private val repository: ArtistRepository
) : ViewModel() {

    val artistDetails = mutableStateOf<Artist?>(null)
    val isLoading = mutableStateOf(false)
    val hasError = mutableStateOf(false)

    fun fetchArtistDetail(artistId: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                artistDetails.value = repository.getArtistDetail(artistId).getOrThrow()
                hasError.value = false
            } catch (e: Exception) {
                hasError.value = true
            } finally {
                isLoading.value = false
            }
        }
    }
}