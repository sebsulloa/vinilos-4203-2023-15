package com.misw.vinilos.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.misw.vinilos.R

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    object Albums : Screen("albums", Icons.Filled.Album ,R.string.tab_albums)
    object Artists : Screen("artists", Icons.Filled.Mic ,R.string.tab_artists)
    object Collectors : Screen("collectors", Icons.Filled.Person ,R.string.tab_collectors)
}


@Composable
fun Screen.title(): String {
    return stringResource(this.resourceId)
}