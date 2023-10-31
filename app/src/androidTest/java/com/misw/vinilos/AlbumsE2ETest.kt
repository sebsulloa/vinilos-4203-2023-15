package com.misw.vinilos

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@OptIn(ExperimentalTestApi::class)
class AlbumsE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testClickAlbumsNavigationItem() {
        composeTestRule.onNodeWithTag("AlbumsNavItem").performClick()
        composeTestRule.onNodeWithTag("topAppBarTitle").assertTextEquals("Albums")
    }

    @Test
    fun testAlbumsLoading() {
        composeTestRule.onNodeWithTag("AlbumsNavItem").performClick()
        composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()
    }

    @Test
    fun testAlbumsDisplay() {
        composeTestRule.onNodeWithTag("AlbumsNavItem").performClick()
        composeTestRule.waitUntilDoesNotExist(hasTestTag("Loading"))
        composeTestRule.onNodeWithText("Buscando América").assertIsDisplayed()
    }

}