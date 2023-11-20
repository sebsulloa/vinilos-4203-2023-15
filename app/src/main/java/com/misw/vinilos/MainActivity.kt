package com.misw.vinilos

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.misw.vinilos.navigation.Screen
import com.misw.vinilos.navigation.title
import com.misw.vinilos.ui.components.AlbumFloatingActionButton
import com.misw.vinilos.ui.components.BottomNavigationItem
import com.misw.vinilos.ui.screens.albums.AlbumCreateScreen
import com.misw.vinilos.ui.screens.albums.AlbumDetailsScreen
import com.misw.vinilos.ui.screens.albums.AlbumsListScreen
import com.misw.vinilos.ui.screens.artists.ArtistDetailsScreen
import com.misw.vinilos.ui.screens.artists.ArtistsListScreen
import com.misw.vinilos.ui.screens.collectors.CollectorsListScreen
import com.misw.vinilos.ui.theme.VinilosTheme
import com.misw.vinilos.viewmodels.AlbumCreateViewModel
import com.misw.vinilos.viewmodels.AlbumsViewModel
import com.misw.vinilos.viewmodels.ArtistDetailsViewModel
import com.misw.vinilos.viewmodels.ArtistsViewModel
import com.misw.vinilos.viewmodels.CollectorsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val albumsViewModel: AlbumsViewModel by viewModels()
            val artistsViewModel: ArtistsViewModel by viewModels()
            val createAlbumViewModel: AlbumCreateViewModel by viewModels()
            val collectorsViewModel: CollectorsViewModel by viewModels()
            val artistDetailsViewModel: ArtistDetailsViewModel by viewModels()

            VinilosTheme {
                Scaffold(
                    topBar = {
                        if (currentDestination?.route == Screen.CreateAlbum.route
                            || currentDestination?.route?.startsWith(Screen.AlbumDetails.route) == true){
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                },
                                title = {
                                    Text(
                                        modifier = Modifier.testTag("topAppBarTitle"),
                                        text = currentDestination?.let { destination ->
                                            when {
                                                destination.route == Screen.CreateAlbum.route -> Screen.CreateAlbum.title()
                                                destination.route?.startsWith(Screen.AlbumDetails.route) == true -> {
                                                    val albumId = navBackStackEntry?.arguments?.getInt("albumId")?: -1
                                                    val selectedAlbum = albumsViewModel.albums.value.find { it.id == albumId }
                                                    selectedAlbum?.name ?: ""
                                                }
                                                else -> ""
                                            }
                                        } ?: ""
                                    )
                                },
                                colors = TopAppBarDefaults.mediumTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = Color.White,
                                    navigationIconContentColor = Color.White
                                )
                            )
                        } else {
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
                                    navigationIconContentColor = Color.White
                                ),
                            )
                        }
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
                    },
                    floatingActionButton = {
                        if (currentDestination?.route == Screen.Albums.route) {
                            AlbumFloatingActionButton(onClick = {
                                navController.navigate(Screen.CreateAlbum.route)
                            })
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Albums.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Albums.route) { AlbumsListScreen(albumsViewModel, navController) }
                        composable(Screen.CreateAlbum.route) { AlbumCreateScreen(createAlbumViewModel) }
                        composable(
                            route = "${Screen.AlbumDetails.route}/{albumId}",
                            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val arguments = requireNotNull(backStackEntry.arguments)
                            val albumId = arguments.getInt("albumId")
                            val selectedAlbum = albumsViewModel.albums.value.find { it.id == albumId }
                            if (selectedAlbum != null) {
                                AlbumDetailsScreen(selectedAlbum, navController)
                            } else {
                                // Handle error, album not found
                            }
                        }

                        composable(Screen.Artists.route) { ArtistsListScreen(artistsViewModel, navController) }
                        composable(
                            route = "${Screen.ArtistDetails.route}/{artistId}",
                            arguments = listOf(navArgument("artistId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val arguments = requireNotNull(backStackEntry.arguments)
                            val artistId = arguments.getInt("artistId")
                            artistDetailsViewModel.fetchArtistDetail(artistId)
                            ArtistDetailsScreen(viewModel = artistDetailsViewModel, artistId = artistId, navController = navController)
                        }
                        composable(Screen.Collectors.route) { CollectorsListScreen(collectorsViewModel) }
                    }
                }
            }
        }
    }
}
