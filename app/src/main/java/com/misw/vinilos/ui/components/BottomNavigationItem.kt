package com.misw.vinilos.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.misw.vinilos.navigation.Screen

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Albums",
                icon = Icons.Filled.Album,
                route = Screen.Albums.route,
            ),
            BottomNavigationItem(
                label = "Artists",
                icon = Icons.Filled.Mic,
                route = Screen.Artists.route
            ),
            BottomNavigationItem(
                label = "Collectors",
                icon = Icons.Filled.Person,
                route = Screen.Collectors.route
            ),
        )
    }
}