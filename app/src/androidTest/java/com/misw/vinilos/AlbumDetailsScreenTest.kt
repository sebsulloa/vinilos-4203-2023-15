package com.misw.vinilos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.misw.vinilos.ui.screens.albums.AlbumDetailsScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDetailsDisplay() {
        // Given
        val album = DataFactory.createAlbum()

        // When
        composeTestRule.setContent { AlbumDetailsScreen(album = album)}

        // Then
        composeTestRule.onNodeWithText(album.recordLabel).assertIsDisplayed()
        composeTestRule.onNodeWithText(album.description).assertIsDisplayed()
    }
}