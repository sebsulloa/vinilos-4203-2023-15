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
import com.misw.vinilos.data.remote.models.Collector
import com.misw.vinilos.ui.screens.collectors.CollectorDetailsScreen
import com.misw.vinilos.viewmodels.CollectorDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Test
    fun testDetailsDisplay() {
        // Given
        val mockViewModel = mockk<CollectorDetailsViewModel>(relaxed = true)
        val collector = DataFactory.createCollector()

        // When
        every { mockViewModel.collectorDetails } returns mutableStateOf<Collector?>(collector)
        every { mockViewModel.isLoading } returns mutableStateOf(false)
        every { mockViewModel.hasError } returns mutableStateOf(false)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CollectorDetailsScreen(viewModel = mockViewModel, collector.id, navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText(collector.email).assertIsDisplayed()
    }
}