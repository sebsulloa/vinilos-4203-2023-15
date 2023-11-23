package com.misw.vinilos.viewmodels

import androidx.compose.runtime.mutableStateOf
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
    private val repository: AlbumRepository
) : ViewModel() {

    // State variables for inputs
    var trackName = mutableStateOf("")
    var trackMinutes = mutableStateOf("")
    var trackSeconds = mutableStateOf("")

    // Error states for each input
    var nameError = mutableStateOf(false)
    var minutesError = mutableStateOf(false)
    var secondsError = mutableStateOf(false)

    // Loading and Error States
    var isLoading = mutableStateOf(false)
    var successMessage = mutableStateOf("")
    var errorMessage = mutableStateOf("")

    fun createTrack(albumId: Int?) {

        if (albumId == null) {
            errorMessage.value = "Album ID is required"
            return
        }

        if (validateInputs()) {
            viewModelScope.launch {
                isLoading.value = true

                val formattedMinutes = String.format("%02d", Integer.parseInt(trackMinutes.value))
                val formattedSeconds = String.format("%02d", Integer.parseInt(trackSeconds.value))

                val trackCreateRequest = TrackCreateRequest(
                    name = trackName.value,
                    duration = "$formattedMinutes:$formattedSeconds"
                )

                repository.createTrack(albumId, trackCreateRequest)
                    .onSuccess {
                        successMessage.value = "Track created successfully"
                        clearInputs()
                    }
                    .onErrorDeserialize<Track, ErrorResponse> { errorResponse ->
                        errorMessage.value = "Error creating track: ${errorResponse.message}"
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
        trackMinutes.value = ""
        trackSeconds.value = ""

        // Reset error states
        nameError.value = false
        minutesError.value = false
        secondsError.value = false
    }

    private fun validateInputs(): Boolean {
        nameError.value = trackName.value.isBlank()
        minutesError.value = !isValidTimeInput(trackMinutes.value)
        secondsError.value = !isValidTimeInput(trackSeconds.value)

        return !(nameError.value || minutesError.value || secondsError.value)
    }

    private fun isValidTimeInput(input: String): Boolean {
        return input.toIntOrNull()?.let { it in 0..59 } ?: false
    }
}
