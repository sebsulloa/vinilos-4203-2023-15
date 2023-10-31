package com.misw.vinilos.ui.screens.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.ui.components.ErrorMessage
import com.misw.vinilos.viewmodels.ArtistsViewModel

@Composable
fun ArtistsListScreen(viewModel: ArtistsViewModel) {
    val artists = viewModel.artists.value
    val isLoading = viewModel.isLoading.value
    val hasError = viewModel.hasError.value

    when {
        isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        hasError -> {
            ErrorMessage(
                modifier = Modifier.fillMaxSize()
            )
        }
        else -> {
            LazyColumn {
                items(artists) { artist ->
                    ArtistListItem(artist = artist)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListItem(artist: Artist) {
    ListItem(
        headlineText = { Text(artist.name) },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(model = artist.image),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = null
            )
        }
    )
}