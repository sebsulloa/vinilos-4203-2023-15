package com.misw.vinilos.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.misw.vinilos.DataFactory
import com.misw.vinilos.data.remote.models.Artist
import com.misw.vinilos.ui.screens.artists.ArtistDetailsScreen
import com.misw.vinilos.viewmodels.ArtistDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Test
    fun testDetailsDisplay() {
        // Given
        val mockViewModel = mockk<ArtistDetailsViewModel>(relaxed = true)
        val artist = DataFactory.createArtist()

        // When
        every { mockViewModel.artistDetails } returns mutableStateOf<Artist?>(artist)
        every { mockViewModel.isLoading } returns mutableStateOf(false)
        every { mockViewModel.hasError } returns mutableStateOf(false)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            ArtistDetailsScreen(viewModel = mockViewModel, artist.id, navController = navController)
        }

// Wait for the loading indicator to disappear

// Then
        composeTestRule.onNodeWithText(artist.birthDate).assertIsDisplayed()
    }
}