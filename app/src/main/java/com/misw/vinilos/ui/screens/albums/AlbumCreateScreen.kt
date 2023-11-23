package com.misw.vinilos.ui.screens.albums

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.misw.vinilos.ui.components.DateSelectionDialog
import com.misw.vinilos.ui.components.DropdownSelector
import com.misw.vinilos.utils.formatDateForDisplay
import com.misw.vinilos.viewmodels.AlbumCreateViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlbumCreateScreen(viewModel: AlbumCreateViewModel) {
    val albumName = viewModel.albumName.value
    val nameError = viewModel.nameError.value

    val albumCover = viewModel.albumCover.value
    val coverError = viewModel.coverError.value

    val albumReleaseDate = viewModel.albumReleaseDate.value
    val releaseDateError = viewModel.releaseDateError.value

    val albumDescription = viewModel.albumDescription.value
    val descriptionError = viewModel.descriptionError.value

    val selectedGenre = viewModel.selectedGenre.value
    val genreError = viewModel.genreError.value

    val selectedRecord = viewModel.selectedRecord.value
    val recordError = viewModel.recordError.value

    val isLoading = viewModel.isLoading.value
    val showDialog = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

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
                value = albumName,
                onValueChange = {
                    viewModel.albumName.value = it
                    viewModel.nameError.value = it.isEmpty()
                },
                label = { Text("Name") },
                isError = nameError,
                singleLine = true,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("AlbumNameField"),
                supportingText = {
                    if (nameError) {
                        Text("Name cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
            OutlinedTextField(
                value = albumCover,
                onValueChange = {
                    viewModel.albumCover.value = it
                    viewModel.coverError.value = it.isEmpty()
                },
                label = { Text("Cover URL") },
                isError = coverError,
                singleLine = true,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("AlbumCoverField"),
                supportingText = {
                    if (coverError) {
                        Text(
                            "Cover URL cannot be empty",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                value = albumDescription,
                onValueChange = {
                    viewModel.albumDescription.value = it
                    viewModel.descriptionError.value = it.isEmpty()
                },
                label = { Text("Description") },
                isError = descriptionError,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("AlbumDescriptionField"),
                supportingText = {
                    if (descriptionError) {
                        Text("Description cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            DateSelectionDialog(
                showDialog = showDialog,
                onDateSelected = { date ->
                    if (date != null) {
                        viewModel.albumReleaseDate.value = formatDateForDisplay(date)
                        viewModel.releaseDateError.value = formatDateForDisplay(date).isEmpty()
                        viewModel.albumReleaseDateMillis.longValue = date
                    }
                }
            )

            OutlinedTextField(
                value = albumReleaseDate,
                onValueChange = {
                    viewModel.albumReleaseDate.value = it
                    viewModel.releaseDateError.value = it.isEmpty()
                },
                label = { Text("Release date") },
                isError = releaseDateError,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable { showDialog.value = true }
                    .testTag("AlbumReleaseDateField"),
                supportingText = {
                    if (releaseDateError) {
                        Text("Release date cannot be empty", color = MaterialTheme.colorScheme.error)
                    } else {
                        Text("dd/MM/YYYY")
                    }
                },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )

            DropdownSelector(
                options = listOf("Classical", "Salsa", "Rock", "Folk"),
                selectedOption = selectedGenre,
                onOptionSelected = { genre ->
                    viewModel.selectedGenre.value = genre
                    viewModel.genreError.value = genre.isEmpty()
                },
                label = "Genre",
                supportingText = {
                    if (genreError) {
                        Text("Genre cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                },
                isError = genreError,
                modifier = Modifier.testTag("AlbumGenreField")
            )

            DropdownSelector(
                options = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records"),
                selectedOption = selectedRecord,
                onOptionSelected = { record ->
                    viewModel.selectedRecord.value = record
                    viewModel.recordError.value = record.isEmpty()
                },
                label = "Record",
                supportingText = {
                    if (recordError) {
                        Text("Record cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                },
                isError = recordError,
                modifier = Modifier.testTag("AlbumRecordField")
            )

            Button(
                onClick = { viewModel.createAlbum() },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .testTag("AlbumSubmitField")
                    .semantics { // Use semantics to provide contentDescription
                        contentDescription = "Create Album Button"
                    },
                enabled = !viewModel.isLoading.value,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            ){
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text("Create Album")
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag("AlbumCreationSnackbar")
        )
    }
}