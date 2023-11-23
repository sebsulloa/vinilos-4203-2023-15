package com.misw.vinilos.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.misw.vinilos.DataFactory
import com.misw.vinilos.ui.screens.collectors.CollectorsListScreen
import com.misw.vinilos.viewmodels.CollectorsViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Test
    fun testLoadingState() {
        // Given
        val mockViewModel = mockk<CollectorsViewModel>(relaxed = true)

        // When
        every { mockViewModel.collectors } returns mutableStateOf(listOf())
        every { mockViewModel.isLoading } returns mutableStateOf(true)
        every { mockViewModel.hasError } returns mutableStateOf(false)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CollectorsListScreen(viewModel = mockViewModel, navController = navController)
        }

        // Then
        composeTestRule.onNodeWithTag("Loading").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        // Given
        val mockViewModel = mockk<CollectorsViewModel>(relaxed = true)

        // When
        every { mockViewModel.collectors } returns mutableStateOf(listOf())
        every { mockViewModel.isLoading } returns mutableStateOf(false)
        every { mockViewModel.hasError } returns mutableStateOf(true)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CollectorsListScreen(viewModel = mockViewModel, navController = navController)
        }

        // Then
        composeTestRule.onNodeWithTag("errorMessage").assertIsDisplayed()
    }

    @Test
    fun testCollectorListState() {
        // Given
        val mockViewModel = mockk<CollectorsViewModel>(relaxed = true)
        val collectors = (1..5).map { DataFactory.createCollector() }

        // When
        every { mockViewModel.collectors } returns mutableStateOf(collectors)
        every { mockViewModel.isLoading } returns mutableStateOf(false)
        every { mockViewModel.hasError } returns mutableStateOf(false)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CollectorsListScreen(viewModel = mockViewModel, navController = navController)
        }

        // Then
        composeTestRule.onNodeWithTag("collectorList").assertIsDisplayed()
    }
}