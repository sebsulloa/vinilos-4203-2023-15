package com.misw.vinilos.viewmodels

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.AlbumCreateRequest
import com.misw.vinilos.data.remote.models.ErrorResponse
import com.misw.vinilos.data.repository.AlbumRepository
import com.misw.vinilos.utils.convertDateFormatForBackend
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.retrofit.serialization.onErrorDeserialize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumCreateViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    // State variables for inputs
    var albumName = mutableStateOf("")
    var albumCover = mutableStateOf("")
    var albumReleaseDate = mutableStateOf("")
    var albumReleaseDateMillis = mutableLongStateOf(0L)
    var albumDescription = mutableStateOf("")
    var selectedGenre = mutableStateOf("")
    var selectedRecord = mutableStateOf("")

    // Error states for each input
    var nameError = mutableStateOf(false)
    var coverError = mutableStateOf(false)
    var releaseDateError = mutableStateOf(false)
    var descriptionError = mutableStateOf(false)
    var genreError = mutableStateOf(false)
    var recordError = mutableStateOf(false)

    // Loading and Error States
    var isLoading = mutableStateOf(false)
    var successMessage = mutableStateOf("")
    var errorMessage = mutableStateOf("")

    fun createAlbum() {
        if (validateInputs()) {
            viewModelScope.launch {
                isLoading.value = true

                val albumCreateRequest = AlbumCreateRequest(
                    name = albumName.value,
                    cover = albumCover.value,
                    releaseDate = convertDateFormatForBackend(albumReleaseDateMillis.longValue),
                    description = albumDescription.value,
                    genre = selectedGenre.value,
                    recordLabel = selectedRecord.value
                )

                repository.createAlbum(albumCreateRequest)
                    .onSuccess {
                        successMessage.value = "Album created successfully"
                        clearInputs()
                    }
                    .onErrorDeserialize<Album, ErrorResponse> { errorResponse ->
                        errorMessage.value = "Error creating album: ${errorResponse.message}"
                    }
                    .onException {
                        errorMessage.value = "Exception occurred: $message"
                    }

                isLoading.value = false
            }
        }
    }

    fun clearInputs() {
        albumName.value = ""
        albumCover.value = ""
        albumReleaseDate.value = ""
        albumDescription.value = ""
        selectedGenre.value = ""
        selectedRecord.value = ""

        // Reset error states
        nameError.value = false
        coverError.value = false
        releaseDateError.value = false
        descriptionError.value = false
        genreError.value = false
        recordError.value = false
    }

    private fun validateInputs(): Boolean {
        nameError.value = albumName.value.isBlank()
        coverError.value = albumCover.value.isBlank()
        releaseDateError.value = albumReleaseDate.value.isBlank() || albumReleaseDateMillis.longValue== 0L
        descriptionError.value = albumDescription.value.isBlank()
        genreError.value = selectedGenre.value.isBlank()
        recordError.value = selectedRecord.value.isBlank()

        return !(nameError.value || coverError.value || releaseDateError.value
                || descriptionError.value || genreError.value || recordError.value)
    }
}
