package com.example.spotme.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit test to test the functionality of SpotMe's UI
 */
@RunWith(AndroidJUnit4::class)
class AddProfileUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testScreen() {

        val addProfileCallback = { name: String, description: String, preferredPayment: String ->
            Unit
            assertEquals("John Doe", name)
            assertEquals("A cool guy", description)
            assertEquals("NONE", preferredPayment)
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            AddProfileScreen(
                postOpNavigation = {},
                addProfileToDatabase = addProfileCallback,
                navController = navController,
            )
        }

        val nameField = composeTestRule.onNodeWithText("Name")
        nameField.performClick()
        nameField.performTextInput("John Doe")

        val descriptionField = composeTestRule.onNodeWithText("Description")
        descriptionField.performClick()
        descriptionField.performTextInput("A cool guy")

        // Dropdown
        val preferredPaymentField = composeTestRule.onNodeWithTag("add_profile_preferred_payment")
        preferredPaymentField.performClick()

        val addButton = composeTestRule.onNodeWithTag("add_profile_button")
        addButton.performClick()
    }
}