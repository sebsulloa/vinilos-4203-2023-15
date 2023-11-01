package com.misw.vinilos

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.misw.vinilos.data.remote.models.Album
import com.misw.vinilos.data.remote.models.Track
import com.misw.vinilos.ui.screens.albums.AlbumsListScreen
import com.misw.vinilos.viewmodels.AlbumsViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        // Given
        val mockViewModel = mockk<AlbumsViewModel>(relaxed = true)

        // When
        every { mockViewModel.albums } returns mutableStateOf(listOf())
        every { mockViewModel.isLoading } returns mutableStateOf(true)
        every { mockViewModel.hasError } returns mutableStateOf(false)

        composeTestRule.setContent {
            AlbumsListScreen(viewModel = mockViewModel)
        }

        // Then
        composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        // Given
        val mockViewModel = mockk<AlbumsViewModel>(relaxed = true)

        // When
        every { mockViewModel.albums } returns mutableStateOf(listOf())
        every { mockViewModel.isLoading } returns mutableStateOf(false)
        every { mockViewModel.hasError } returns mutableStateOf(true)

        composeTestRule.setContent {
            AlbumsListScreen(viewModel = mockViewModel)
        }

        // Then
        composeTestRule.onNodeWithTag("errorMessage").assertIsDisplayed()
    }

    @Test
    fun testAlbumListState() {
        // Given
        val mockViewModel = mockk<AlbumsViewModel>(relaxed = true)
        val album = Album(
            id = 100,
            name = "Buscando América",
            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
            releaseDate = "1984-08-01T00:00:00.000Z",
            description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984.",
            genre = "Salsa",
            recordLabel = "Elektra",
            tracks = listOf(
                Track(id = 100, name = "Decisiones", duration = "5:05"),
                Track(id = 101, name = "Desapariciones", duration = "6:29")
            ),
            performers = emptyList()
        )

        // When
        every { mockViewModel.albums } returns mutableStateOf(listOf(album))
        every { mockViewModel.isLoading } returns mutableStateOf(false)
        every { mockViewModel.hasError } returns mutableStateOf(false)

        composeTestRule.setContent {
            AlbumsListScreen(viewModel = mockViewModel)
        }

        // Then
        composeTestRule.onNodeWithTag("albumList").assertIsDisplayed()
    }
}