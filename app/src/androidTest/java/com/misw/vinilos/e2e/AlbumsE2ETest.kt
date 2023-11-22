package com.misw.vinilos.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.misw.vinilos.pages.CreateAlbumPage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.misw.vinilos.DataFactory
import com.misw.vinilos.MainActivity
import com.misw.vinilos.pages.CreateTrackPage

@RunWith(AndroidJUnit4::class)
@LargeTest
class AlbumsE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val createAlbumPage by lazy { CreateAlbumPage(composeTestRule) }
    private val createTrackPage by lazy { CreateTrackPage(composeTestRule) }

    @Test
    fun testClickAlbumsNavigationItem() {
        // When
        composeTestRule.onNodeWithTag("AlbumsNavItem").performClick()

        // Then
        composeTestRule.onNodeWithTag("topAppBarTitle").assertTextEquals("Albums")
    }

    @Test
    fun testAlbumsLoading() {
        // When
        composeTestRule.onNodeWithTag("AlbumsNavItem").performClick()

        // Then
        try {
            composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()
        } catch (e: AssertionError) {
            composeTestRule.onNodeWithTag("albumList").assertIsDisplayed()
        }
    }

    @Test
    fun testCreateAlbum() {
        // Given
        val randomAlbum = DataFactory.createAlbumForm()

        // When
        with(createAlbumPage) {
            navigateToAlbumCreationScreen()
            fillAlbumFormWith(randomAlbum)
            submitAlbumForm()
        }

        // Then
        createAlbumPage.verifyAlbumCreationSuccess()
    }

    @Test
    fun testClickFirstAlbumInList() {
        // Given
        val randomTrack = DataFactory.createTrackForm()

        // When
        with(createTrackPage) {
            navigateToTrackCreationScreen()
            fillTrackFormWith(randomTrack)
            submitTrackForm()
        }

        // Then
        createTrackPage.verifyTrackCreationSuccess()
    }
}
