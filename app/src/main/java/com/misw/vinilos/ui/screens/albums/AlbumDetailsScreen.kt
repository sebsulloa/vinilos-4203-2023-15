package com.misw.vinilos.ui.screens.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.models.Track
import com.misw.vinilos.navigation.Screen
import com.misw.vinilos.ui.screens.artists.ArtistListItem
import com.misw.vinilos.utils.formatDateFromString

@Composable
fun AlbumDetailsScreen(album: Album, navController: NavController) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(modifier = Modifier.height(16.dp))


            // Album Image
            Image(
                painter = rememberAsyncImagePainter(model = album.cover),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )

            // Album Information
            AlbumInfoSection(album = album)
        }

        // Tracks Section
        item {
            SectionTitle(title = "Tracks")
        }
        item {
            AlbumTracksList(tracks = album.tracks)
        }

        // Performers Section
        item {
            SectionTitle(title = "Artists")
        }

        item {
            AlbumArtistsList(performers = album.performers, navController =  navController)
        }
    }
}

@Composable
fun AlbumInfoSection(album: Album) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AlbumInfoItem(label = "Release Date", value = formatDateFromString(album.releaseDate), icon = Icons.Default.Event)
        AlbumInfoItem(label = "Description", value = album.description, icon = Icons.Default.Info)
        AlbumInfoItem(label = "Genre", value = album.genre, icon = Icons.Default.MusicNote)
        AlbumInfoItem(
            label = "Record Label",
            value = album.recordLabel,
            icon = Icons.AutoMirrored.Default.Label
        )
    }
}

@Composable
fun AlbumInfoItem(label: String, value: String, icon: ImageVector) {
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

@Composable
fun AlbumTracksList(tracks: List<Track>) {
    var height = 50.dp
    val trackCount = tracks.size
    if (trackCount >= 4) {
        height = 240.dp
    } else if (trackCount > 0) {
        height = 70.dp * trackCount
    }

    LazyColumn(
        modifier = Modifier.height(height)
    ) {
        items(tracks) { track ->
            AlbumTrackItem(track = track)
        }
    }
}

@Composable
fun AlbumTrackItem(track: Track) {
    ListItem(
        headlineContent = { Text(track.name, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        supportingContent = { Text(track.duration, fontSize = 14.sp) },
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.PlayArrow, contentDescription = null
            )
        }
    )
}

@Composable
fun AlbumArtistsList(performers: List<Artist>, navController: NavController) {
    var height = 50.dp
    val trackCount = performers.size
    if (trackCount >= 4) {
        height = 240.dp
    } else if (trackCount > 0) {
        height = 70.dp * trackCount
    }

    LazyColumn(
        modifier = Modifier.height(height)
    ) {
        items(performers) { artist ->
            ArtistListItem(artist = artist)
            {
                navController.navigate(Screen.ArtistDetails.route + "/${artist.id}")
            }
        }
    }
}