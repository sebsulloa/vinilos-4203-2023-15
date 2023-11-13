package com.misw.vinilos.ui.screens.albums

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.models.Track
import com.misw.vinilos.ui.screens.artists.ArtistListItem

@Composable
fun AlbumDetailsScreen(album: Album) {
    Column() {
        // Album Image
        /*Image(
            painter = rememberCoilPainter(request = album.cover),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = MaterialTheme.shapes.medium)
        )*/

        // Album Information
        AlbumInfoSection(album = album)

        // Tracks Section
        SectionTitle(title = "Tracks")
        AlbumTracksList(tracks = album.tracks)

        // Performers Section
        SectionTitle(title = "Artists")
        AlbumArtistsList(performers = album.performers)
    }
}

@Composable
fun AlbumInfoSection(album: Album) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AlbumInfoItem(label = "Fecha de lanzamiento", value = album.releaseDate, icon = Icons.Default.Event)
        AlbumInfoItem(label = "Descripción", value = album.description, icon = Icons.Default.TextFields)
        AlbumInfoItem(label = "Género", value = album.genre, icon = Icons.Default.Waves)
        AlbumInfoItem(label = "Sello Discografico", value = album.recordLabel, icon = Icons.AutoMirrored.Default.Label)
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
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(text = value, fontSize = 16.sp)
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
        fontSize = 20.sp
    )
}

@Composable
fun AlbumTracksList(tracks: List<Track>) {
    LazyColumn {
        items(tracks) { track ->
            AlbumTrackItem(track = track)
        }
    }
}

@Composable
fun AlbumTrackItem(track: Track) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = track.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = track.duration, fontSize = 14.sp)
    }
}

@Composable
fun AlbumArtistsList(performers: List<Artist>) {
    LazyColumn {
        items(performers) { artist ->
            ArtistListItem(artist = artist)
        }
    }
}