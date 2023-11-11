package com.misw.vinilos.ui.screens.albums

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.misw.vinilos.ui.components.DropdownSelector
import com.misw.vinilos.ui.components.MaskedDateInput
import com.misw.vinilos.viewmodels.AlbumCreateViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = albumName,
            onValueChange = {
                viewModel.albumName.value = it
                viewModel.nameError.value = it.isEmpty()
            },
            label = { Text("Album Name") },
            isError = nameError,
            singleLine = true,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            supportingText = {
                if (nameError) {
                    Text("Album name cannot be empty", color = MaterialTheme.colorScheme.error)
                }
            }
        )
        OutlinedTextField(
            value = albumCover,
            onValueChange = {
                viewModel.albumCover.value = it
                viewModel.coverError.value = it.isEmpty()
            },
            label = { Text("Album Cover URL") },
            isError = coverError,
            singleLine = true,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            supportingText = {
                if (coverError) {
                    Text("Album cover URL cannot be empty", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        MaskedDateInput(
            date = albumReleaseDate,
            onDateChange = { it ->
                viewModel.albumReleaseDate.value = it
                viewModel.releaseDateError.value = it.isEmpty()
            },
            isError = releaseDateError,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            supportingText = {
                if (releaseDateError) {
                    Text("Album release date cannot be empty", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = albumDescription,
            onValueChange = {
                viewModel.albumDescription.value = it
                viewModel.descriptionError.value = it.isEmpty()
            },
            label = { Text("Album Description") },
            isError = descriptionError,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            supportingText = {
                if (descriptionError) {
                    Text("Description cannot be empty", color = MaterialTheme.colorScheme.error)
                }
            }
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
            isError = genreError
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
            isError = recordError
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.createAlbum() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
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
                Text("Create Album")
            }
        }
    }
}