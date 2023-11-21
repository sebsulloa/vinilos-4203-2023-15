package com.misw.vinilos.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.remote.models.ErrorResponse
import com.misw.vinilos.data.remote.models.Track
import com.misw.vinilos.data.remote.models.TrackCreateRequest
import com.misw.vinilos.data.repository.AlbumRepository
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.retrofit.serialization.onErrorDeserialize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackCreateViewModel @Inject constructor(
    private val repository: AlbumRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val albumId: Int = savedStateHandle["albumId"] ?: throw IllegalStateException("Album ID not found")

    // State variables for inputs
    var trackName = mutableStateOf("")
    var trackMinutes = mutableIntStateOf(0)
    var trackSeconds = mutableIntStateOf(0)

    // Error states for each input
    var nameError = mutableStateOf(false)
    var minutesError = mutableStateOf(false)
    var secondsError = mutableStateOf(false)

    // Loading and Error States
    var isLoading = mutableStateOf(false)
    var successMessage = mutableStateOf("")
    var errorMessage = mutableStateOf("")

    fun createTrack() {
        if (validateInputs()) {
            viewModelScope.launch {
                isLoading.value = true

                val formattedMinutes = String.format("%02d", trackMinutes.value)
                val formattedSeconds = String.format("%02d", trackSeconds.value)

                val trackCreateRequest = TrackCreateRequest(
                    name = trackName.value,
                    duration = "$formattedMinutes:$formattedSeconds"
                )

                repository.createTrack(albumId, trackCreateRequest)
                    .onSuccess {
                        successMessage.value = "Album created successfully"
                        clearInputs()
                    }
                    .onErrorDeserialize<Track, ErrorResponse> { errorResponse ->
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
        trackName.value = ""
        trackMinutes.value = 0
        trackSeconds.value = 0

        // Reset error states
        nameError.value = false
        minutesError.value = false
        secondsError.value = false
    }

    private fun validateInputs(): Boolean {
        nameError.value = trackName.value.isBlank()
        minutesError.value = trackMinutes.value < 0 || trackMinutes.value > 59
        secondsError.value = trackSeconds.value < 0 || trackSeconds.value > 59

        return !(nameError.value || minutesError.value || secondsError.value)
    }
}
