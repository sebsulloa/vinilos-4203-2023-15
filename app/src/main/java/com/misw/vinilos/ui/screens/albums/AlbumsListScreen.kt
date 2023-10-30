package com.misw.vinilos.ui.screens.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.misw.vinilos.R

data class Album(
    val avatarResId: Int,
    val headline: String,
    val supportingText: String
)

val sampleAlbums = List(10) {
    Album(
        avatarResId = R.drawable.ic_launcher_foreground,
        headline = "Album $it",
        supportingText = "Genre $it",
    )
}

@Composable
fun AlbumsListScreen() {
    LazyColumn {
        items(sampleAlbums) { album ->
            AlbumListItem(album = album)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListItem(album: Album) {
    ListItem(
        headlineText = { Text(album.headline) },
        supportingText = { Text(album.supportingText) },
        leadingContent = {
            Image(
                painter = painterResource(id = album.avatarResId),
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