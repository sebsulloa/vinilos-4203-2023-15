package com.misw.vinilos.ui.screens.albums

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.misw.vinilos.viewmodels.TrackCreateViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackCreateScreen(viewModel: TrackCreateViewModel, albumId: Int?) {
    val trackName = viewModel.trackName.value
    val nameError = viewModel.nameError.value

    val trackMinutes = viewModel.trackMinutes.intValue
    val minutesError = viewModel.minutesError.value

    val trackSeconds = viewModel.trackSeconds.intValue
    val secondsError = viewModel.secondsError.value

    val isLoading = viewModel.isLoading.value
    val snackbarHostState = remember { SnackbarHostState() }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = viewModel.successMessage.value) {
        if (viewModel.successMessage.value.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = viewModel.successMessage.value,
                duration = SnackbarDuration.Short
            )
            viewModel.successMessage.value = ""
        }
    }

    LaunchedEffect(key1 = viewModel.errorMessage.value) {
        if (viewModel.errorMessage.value.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = viewModel.errorMessage.value,
                duration = SnackbarDuration.Short
            )
            viewModel.errorMessage.value = ""
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = trackName,
                onValueChange = {
                    viewModel.trackName.value = it
                    viewModel.nameError.value = it.isEmpty()
                },
                label = { Text("Name") },
                isError = nameError,
                singleLine = true,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("TrackNameField"),
                supportingText = {
                    if (nameError) {
                        Text("Name cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
            OutlinedTextField(
                value = trackMinutes.toString(),
                onValueChange = {
                    viewModel.trackMinutes.intValue = it.toIntOrNull() ?: 0
                    viewModel.minutesError.value = it.isEmpty()
                },
                label = { Text("Minutes") },
                isError = minutesError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("TrackMinutesField"),
                supportingText = {
                    if (minutesError) {
                        Text(
                            "Invalid input",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                value = trackSeconds.toString(),
                onValueChange = {
                    viewModel.trackSeconds.intValue = it.toIntOrNull() ?: 0
                    viewModel.secondsError.value = it.isEmpty()
                },
                label = { Text("Seconds") },
                isError = secondsError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("TrackSecondsField"),
                supportingText = {
                    if (minutesError) {
                        Text(
                            "Invalid input",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.createTrack(albumId)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("TrackSubmitField"),
                enabled = !viewModel.isLoading.value,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Create Track")
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag("TrackCreationSnackbar")
        )
    }
}