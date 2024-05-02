package com.example.spotme.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spotme.data.PaymentType
import com.example.spotme.data.Profile
import com.example.spotme.viewmodels.DetailsUiState
import com.example.spotme.viewmodels.FilterType
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

/**
 * Unit test to test the functionality of SpotMe's UI
 */
@RunWith(AndroidJUnit4::class)
class DetailsUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testScreen() {
        val profiles: List<Profile> = listOf(
            Profile(0, "Austin", "A cool guy", 10.0, listOf(), PaymentType.NONE, Date(), false),
            Profile(1, "Bob", "Another cool guy", -10.0, listOf(), PaymentType.NONE, Date(), false),
        )
        val onProfileClick = { profileID: Long ->
            assertEquals(profileID, 0)
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            DetailsScreen(
                uiState = DetailsUiState(
                    currentProfileId = 0,
                    filterType = FilterType.NONE,
                    filterProfiles = profiles,
                ),
                navController = navController,
                onProfilePressed = onProfileClick,
                onFilterAmountHighPressed = {},
                onFilterAmountLowPressed = {},
                onAddPressed = ({ _ -> }),
            )
        }

        composeTestRule.onNodeWithText("Austin").assertExists()
        composeTestRule.onNodeWithText("$10.00").assertExists()

        composeTestRule.onNodeWithText("Bob").assertExists()
        composeTestRule.onNodeWithText("-$10.00").assertExists()

        composeTestRule.onNodeWithText("Austin").performClick()
    }
}