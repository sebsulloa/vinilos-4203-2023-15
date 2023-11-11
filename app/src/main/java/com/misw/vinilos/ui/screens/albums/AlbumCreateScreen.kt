package com.misw.vinilos.ui.screens.albums

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.misw.vinilos.ui.components.DropdownSelector
import com.misw.vinilos.ui.components.MaskedDateInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumCreateScreen() {
    var albumName by remember { mutableStateOf("") }
    var albumCover by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var coverError by remember { mutableStateOf(false) }

    var albumReleaseDate by remember { mutableStateOf("") }
    var releaseDateError by remember { mutableStateOf(false) }

    var albumDescription by remember { mutableStateOf("") }
    var descriptionError by remember { mutableStateOf(false) }

    var selectedGenre by remember { mutableStateOf("") }
    var genreError by remember { mutableStateOf(false) }

    var selectedRecord by remember { mutableStateOf("") }
    var recordError by remember { mutableStateOf(false) }

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
                albumName = it
                nameError = it.isBlank()
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
                albumCover = it
                coverError = it.isBlank() // Validates the album cover URL
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
                albumReleaseDate = it
                releaseDateError = it.isBlank()
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
                albumDescription = it
                descriptionError = it.isBlank() // Validates that the description is not empty
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
            options = listOf("CLASSICAL", "SALSA", "ROCK", "FOLK"),
            selectedOption = selectedGenre,
            onOptionSelected = { genre ->
                selectedGenre = genre
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
                selectedRecord = record
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
            onClick = {
                nameError = albumName.isBlank()
                coverError = albumCover.isBlank()
                releaseDateError = albumReleaseDate.isBlank()
                descriptionError = albumDescription.isBlank()
                genreError = selectedGenre.isBlank()
                recordError = selectedRecord.isBlank()

                if (!nameError && !coverError && !releaseDateError && !descriptionError && !genreError && !recordError) {
                    // Handle album creation logic with the new description field
                    // TODO: Add logic here
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text("Create Album")
        }
    }
}