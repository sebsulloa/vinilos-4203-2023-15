package com.misw.vinilos.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.misw.vinilos.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistsE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testClickArtistsNavigationItem() {
        // When
        composeTestRule.onNodeWithTag("ArtistsNavItem").performClick()

        // Then
        composeTestRule.onNodeWithTag("topAppBarTitle").assertTextEquals("Artists")
    }

    @Test
    fun testArtstsLoading() {
        // When
        composeTestRule.onNodeWithTag("ArtistsNavItem").performClick()

        // Then
        try {
            composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()
        } catch (e: AssertionError) {
            composeTestRule.onNodeWithTag("artistList").assertIsDisplayed()
        }
    }
}