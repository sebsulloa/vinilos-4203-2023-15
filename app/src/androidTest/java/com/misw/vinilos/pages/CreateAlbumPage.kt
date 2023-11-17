package com.misw.vinilos.pages

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher.Companion.keyIsDefined
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.misw.vinilos.data.remote.models.Album

class CreateAlbumPage(private val composeTestRule: ComposeContentTestRule) {
    fun navigateToAlbumCreationScreen() {
        composeTestRule.onNodeWithTag("CreateAlbumFAB").performClick()
    }

    fun fillAlbumFormWith(album: Album) {
        composeTestRule.onNodeWithTag("AlbumNameField").performTextInput(album.name)
        composeTestRule.onNodeWithTag("AlbumCoverField").performTextInput(album.cover)
        composeTestRule.onNodeWithTag("AlbumDescriptionField").performTextInput(album.description)
        selectReleaseDate()
        selectGenre(album.genre)
        selectRecordLabel(album.recordLabel)
    }

    private fun selectReleaseDate() {
        composeTestRule.onNodeWithTag("AlbumReleaseDateField").performClick()
        composeTestRule.onNodeWithTag("confirm_button").performClick()
    }

    private fun selectGenre(genre: String) {
        composeTestRule.onNodeWithTag("AlbumGenreField").performClick()
        composeTestRule.onNode(
            hasText(genre).and(
                hasAnyAncestor(
                    keyIsDefined(
                        SemanticsProperties.IsPopup
                    )
                )
            )
        ).performClick()
    }

    private fun selectRecordLabel(recordLabel: String) {
        composeTestRule.onNodeWithTag("AlbumRecordField").performClick()
        composeTestRule.onNode(
            hasText(recordLabel).and(
                hasAnyAncestor(
                    keyIsDefined(
                        SemanticsProperties.IsPopup
                    )
                )
            )
        ).performClick()
    }

    fun submitAlbumForm() {
        composeTestRule.onNodeWithTag("AlbumSubmitField").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun verifyAlbumCreationSuccess() {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag("AlbumCreationSnackbar"))
    }
}