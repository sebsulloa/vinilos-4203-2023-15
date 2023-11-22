package com.misw.vinilos.ui.screens.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.navigation.Screen
import com.misw.vinilos.ui.components.ErrorMessage
import com.misw.vinilos.viewmodels.ArtistsViewModel

@Composable
fun ArtistsListScreen(viewModel: ArtistsViewModel, navController: NavController) {
    val artists = viewModel.artists.value
    val isLoading = viewModel.isLoading.value
    val hasError = viewModel.hasError.value
    val isRefreshing = remember { mutableStateOf(false) }

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
                modifier = (Modifier
                    .fillMaxSize()
                    .testTag("errorMessage"))
            )
        }
        else -> {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
                onRefresh = {
                    isRefreshing.value = true
                    viewModel.fetchArtists()
                    isRefreshing.value = false
                }
            ) {
                LazyColumn(modifier = Modifier.testTag("artistList")) {
                    items(artists) { artist ->
                        ArtistListItem(artist = artist)
                        {
                            navController.navigate(Screen.ArtistDetails.route + "/${artist.id}")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListItem(artist: Artist, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        headlineContent = { Text(artist.name) },
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