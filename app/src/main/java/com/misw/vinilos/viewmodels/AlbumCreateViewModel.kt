package com.misw.vinilos.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.remote.models.AlbumCreateRequest
import com.misw.vinilos.data.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumCreateViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    // State variables for inputs
    var albumName = mutableStateOf("Name test")
    var albumCover = mutableStateOf("https://m.media-amazon.com/images/I/716FcW7qgSL._UF1000,1000_QL80_.jpg")
    var albumReleaseDate = mutableStateOf("1111/11/11")
    var albumDescription = mutableStateOf("Description test")
    var selectedGenre = mutableStateOf("Classical")
    var selectedRecord = mutableStateOf("Sony Music")

    // Error states for each input
    var nameError = mutableStateOf(false)
    var coverError = mutableStateOf(false)
    var releaseDateError = mutableStateOf(false)
    var descriptionError = mutableStateOf(false)
    var genreError = mutableStateOf(false)
    var recordError = mutableStateOf(false)

    // Loading and Error States
    var isLoading = mutableStateOf(false)
    var hasError = mutableStateOf(false)

    fun createAlbum() {
        if (validateInputs()) {
            viewModelScope.launch {
                try {
                    isLoading.value = true
                    val albumCreateRequest = AlbumCreateRequest(
                        name = albumName.value,
                        cover = albumCover.value,
                        releaseDate = albumReleaseDate.value,
                        description = albumDescription.value,
                        genre = selectedGenre.value,
                        recordLabel = selectedRecord.value
                    )
                    repository.createAlbum(albumCreateRequest)
                    hasError.value = false
                } catch (e: Exception) {
                    hasError.value = true
                } finally {
                    isLoading.value = false
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        nameError.value = albumName.value.isBlank()
        coverError.value = albumCover.value.isBlank()
        releaseDateError.value = albumReleaseDate.value.isBlank()
        descriptionError.value = albumDescription.value.isBlank()
        genreError.value = selectedGenre.value.isBlank()
        recordError.value = selectedRecord.value.isBlank()

        return !(nameError.value || coverError.value || releaseDateError.value
                || descriptionError.value || genreError.value || recordError.value)
    }
}
