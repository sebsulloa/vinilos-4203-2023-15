package com.misw.vinilos.pages

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.misw.vinilos.data.remote.models.TrackForm

class CreateTrackPage(private val composeTestRule: ComposeContentTestRule) {

    fun navigateToTrackCreationScreen() {
        composeTestRule.onNodeWithTag("AlbumsNavItem").performClick()

        // Wait for the loading indicator to disappear
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("Loading").fetchSemanticsNodes().isEmpty()
        }

        // Click on the first album item
        composeTestRule.onNodeWithTag("albumListItem-1").performClick()

        // Wait for the loading indicator to disappear
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("Loading").fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onNodeWithContentDescription("Create Track").performClick()
    }

    fun fillTrackFormWith(track: TrackForm) {
        composeTestRule.onNodeWithTag("TrackNameField").performTextInput(track.name)
        composeTestRule.onNodeWithTag("TrackMinutesField").performTextInput(track.minutes.toString())
        composeTestRule.onNodeWithTag("TrackSecondsField").performTextInput(track.seconds.toString())
    }

    fun submitTrackForm() {
        composeTestRule.onNodeWithTag("TrackSubmitField").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun verifyTrackCreationSuccess() {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag("TrackCreationSnackbar"))
    }
}