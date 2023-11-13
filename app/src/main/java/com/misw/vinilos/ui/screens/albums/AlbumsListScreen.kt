package com.misw.vinilos.ui.screens.albums

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.ui.components.ErrorMessage
import com.misw.vinilos.viewmodels.AlbumsViewModel

@Composable
fun AlbumsListScreen(viewModel: AlbumsViewModel) {
    val albums = viewModel.albums.value
    val isLoading = viewModel.isLoading.value
    val hasError = viewModel.hasError.value

    when {
        isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("Loading"))
            }
        }
        hasError -> {
            ErrorMessage(
                modifier = (Modifier.fillMaxSize().testTag("errorMessage")),
            )
        }
        else -> {
            LazyColumn(modifier = Modifier.testTag("albumList")) {
                items(albums) { album ->
                    AlbumListItem(album = album)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListItem(album: Album) {
    ListItem(
        headlineContent = { Text(album.name) },
        supportingContent = { Text(album.genre) },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(model = album.cover),
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