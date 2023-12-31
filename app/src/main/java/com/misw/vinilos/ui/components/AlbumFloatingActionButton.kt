package com.misw.vinilos.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun AlbumFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.testTag("CreateAlbumFAB"),
        onClick = { onClick() },
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Album")
    }
}