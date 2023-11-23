package com.misw.vinilos.ui.screens.collectors

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
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.data.remote.models.Collector
import com.misw.vinilos.navigation.Screen
import com.misw.vinilos.ui.components.ErrorMessage
import com.misw.vinilos.ui.screens.artists.ArtistListItem
import com.misw.vinilos.utils.formatDateFromString
import com.misw.vinilos.viewmodels.CollectorDetailsViewModel

@Composable
fun CollectorDetailsScreen(viewModel: CollectorDetailsViewModel, collectorId: Int, navController: NavController) {

    val collectorDetails = viewModel.collectorDetails.value
    val isLoading = viewModel.isLoading.value
    val hasError = viewModel.hasError.value
    val isRefreshing = remember { mutableStateOf(false) }

    LaunchedEffect(collectorId) {
        viewModel.fetchCollectorDetail(collectorId)
    }

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
                    viewModel.fetchCollectorDetail(collectorId)
                    isRefreshing.value = false
                }
            ) {
                if(collectorDetails != null) {
                    CollectorDetails(collectorDetails, navController)
                }
            }
        }
    }
}

@Composable
fun CollectorDetails(collectorDetails: Collector, navController: NavController) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            // Collector Information
            CollectorInfoSection(collectorDetails = collectorDetails)
        }

        // Artists Section
        item {
            SectionTitle(title = "Favorite Artists")
        }
        item {
            CollectorArtistsList(artists = collectorDetails.favoritePerformers, navController = navController)
        }
    }
}

@Composable
fun CollectorInfoSection(collectorDetails: Collector) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CollectorInfoItem(label = "Phone number", value = formatDateFromString(collectorDetails.telephone), icon = Icons.Default.Smartphone)
        CollectorInfoItem(label = "Email", value = collectorDetails.email, icon = Icons.Default.AlternateEmail)

    }
}

@Composable
fun CollectorInfoItem(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = "$label Icon", modifier = Modifier.size(24.dp))
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
fun CollectorArtistsList(artists: List<Artist>, navController: NavController) {
    var height = 50.dp
    val artistCount = artists.size
    if (artistCount >= 6) {
        height = 360.dp
    } else if (artistCount > 0) {
        height = 70.dp * artistCount
    }

    LazyColumn(
        modifier = Modifier.height(height)
    ) {
        items(artists) { artist ->
            ArtistListItem(artist = artist)
            {
                navController.navigate(Screen.ArtistDetails.route + "/${artist.id}")
            }
        }
    }
}
