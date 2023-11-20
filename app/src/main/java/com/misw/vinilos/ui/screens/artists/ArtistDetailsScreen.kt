package com.misw.vinilos.ui.screens.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.navigation.Screen
import com.misw.vinilos.ui.components.ErrorMessage
import com.misw.vinilos.ui.screens.albums.AlbumListItem
import com.misw.vinilos.utils.formatDateFromString
import com.misw.vinilos.viewmodels.ArtistDetailsViewModel

@Composable
fun ArtistDetailsScreen(viewModel: ArtistDetailsViewModel, artistId: Int, navController: NavController) {

    val artistDetails = viewModel.artistDetails.value
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
                modifier = (Modifier.fillMaxSize().testTag("errorMessage"))
            )
        }
        else -> {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
                onRefresh = {
                    isRefreshing.value = true
                    viewModel.fetchArtistDetail(artistId)
                    isRefreshing.value = false
                }
            ) {
                if(artistDetails != null) {
                    ArtistDetails(artistDetails, navController)
                }
            }
        }
    }
}

@Composable
fun ArtistDetails(artistDetails: Artist, navController: NavController) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(modifier = Modifier.height(16.dp))


            // Artist Image
            Image(
                painter = rememberAsyncImagePainter(model = artistDetails.image),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )

            // Artist Information
            ArtistInfoSection(artistDetails = artistDetails)
        }

        // Albums Section
        item {
            SectionTitle(title = "Albums")
        }
        item {
            ArtistAlbumsList(albums = artistDetails.albums, navController = navController)
        }
    }
}

@Composable
fun ArtistAlbumsList(albums: List<Album>, navController: NavController) {
    var height = 50.dp
    val albumCount = albums.size
    if (albumCount >= 6) {
        height = 360.dp
    } else if (albumCount > 0) {
        height = 70.dp * albumCount
    }

    LazyColumn(
        modifier = Modifier.height(height)
    ) {
        items(albums) { album ->
            AlbumListItem(album = album)
            {
                navController.navigate(Screen.AlbumDetails.route + "/${album.id}")
            }
        }
    }
}

@Composable
fun ArtistInfoSection(artistDetails: Artist) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ArtistInfoItem(label = "Birth Date", value = formatDateFromString(artistDetails.birthDate), icon = Icons.Default.Event)
        ArtistInfoItem(label = "Description", value = artistDetails.description, icon = Icons.Default.Info)

    }
}

@Composable
fun ArtistInfoItem(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = value, fontSize = 12.sp)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}
