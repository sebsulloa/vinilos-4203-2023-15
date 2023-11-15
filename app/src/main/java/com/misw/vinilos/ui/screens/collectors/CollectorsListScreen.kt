package com.misw.vinilos.ui.screens.collectors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.misw.vinilos.data.remote.models.Collector
import com.misw.vinilos.ui.components.ErrorMessage
import com.misw.vinilos.viewmodels.CollectorsViewModel

@Composable
fun CollectorsListScreen(viewModel: CollectorsViewModel) {
    val collectors = viewModel.collectors.value
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
                    viewModel.fetchCollectors() // Call your fetch function
                    isRefreshing.value = false
                }
            ) {
                LazyColumn(modifier = Modifier.testTag("collectorList")) {
                    items(collectors) { collector ->
                        CollectorListItem(collector = collector)
                    }
                }
            }
        }
    }
}

@Composable
fun CollectorListItem(collector: Collector) {
    ListItem(
        headlineContent = { Text(collector.name) },
        supportingContent = { Text(collector.email, fontSize = 14.sp) },
        leadingContent = {
            Surface(
                shape = CircleShape,
                modifier = Modifier.size(32.dp),
                color = MaterialTheme.colorScheme.secondary
            )
            {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = collector.name.first().toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = null
            )
        }
    )
}

