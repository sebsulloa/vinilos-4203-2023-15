package com.misw.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.misw.vinilos.navigation.Screen
import com.misw.vinilos.navigation.title
import com.misw.vinilos.ui.components.BottomNavigationItem
import com.misw.vinilos.ui.screens.albums.AlbumsListScreen
import com.misw.vinilos.ui.screens.artists.ArtistsListScreen
import com.misw.vinilos.ui.screens.collectors.CollectorsListScreen
import com.misw.vinilos.ui.theme.VinilosTheme
import com.misw.vinilos.viewmodels.AlbumsViewModel
import com.misw.vinilos.viewmodels.ArtistsViewModel
import com.misw.vinilos.viewmodels.CollectorsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val albumsViewModel: AlbumsViewModel by viewModels()
            val artistsViewModel: ArtistsViewModel by viewModels()
            val collectorsViewModel: CollectorsViewModel by viewModels()

            VinilosTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(

                            title = {
                                Text(modifier = Modifier.testTag("topAppBarTitle"),
                                    text = currentDestination?.route?.let { route ->
                                        when (route) {
                                            Screen.Albums.route -> Screen.Albums.title()
                                            Screen.Artists.route -> Screen.Artists.title()
                                            Screen.Collectors.route -> Screen.Collectors.title()
                                            else -> Screen.Albums.title()
                                        }
                                    } ?: Screen.Albums.title())
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White,
                            ),
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            BottomNavigationItem().bottomNavigationItems()
                                .forEachIndexed { _, navigationItem ->
                                    NavigationBarItem(
                                        modifier = Modifier.testTag("${navigationItem.label}NavItem"),
                                        selected = navigationItem.route == currentDestination?.route,
                                        label = {
                                            Text(navigationItem.label)
                                        },
                                        icon = {
                                            Icon(
                                                navigationItem.icon,
                                                contentDescription = navigationItem.label
                                            )
                                        },
                                        onClick = {
                                            navController.navigate(navigationItem.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Albums.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Albums.route) { AlbumsListScreen(albumsViewModel) }
                        composable(Screen.Artists.route) { ArtistsListScreen(artistsViewModel) }
                        composable(Screen.Collectors.route) { CollectorsListScreen(collectorsViewModel) }
                    }
                }
            }
        }
    }
}
