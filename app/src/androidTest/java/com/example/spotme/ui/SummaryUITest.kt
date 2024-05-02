package com.example.spotme.ui

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spotme.data.DebugData
import com.example.spotme.database.LocalDatabase
import com.example.spotme.database.Repository
import com.example.spotme.database.RepositoryInterface
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit test to test the functionality of SpotMe's UI
 */
@RunWith(AndroidJUnit4::class)
class SummaryUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var db: LocalDatabase
    private lateinit var repository: RepositoryInterface

    @Before
    fun setupDB() = runBlocking {
        // Get Database
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LocalDatabase::class.java
        ).build()
        repository = Repository.getRepository(db)

        // Reset DB to initial data
        DebugData.resetInitialDataAsync(db)
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun testScreen() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            SummaryScreen(
                repository = repository,
                navController = navController,
                onPrimaryDebtorClicked = {},
                onPrimaryCreditorClicked = {},
                submitTransaction = ({ _, _, _ -> }),
            )
        }

        composeTestRule.onNodeWithText("Overall Balance: ").assertExists()
        composeTestRule.onNodeWithText("$30.00").assertExists()
    }

    @Test
    fun testPrimaryDebtor() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            SummaryScreen(
                repository = repository,
                navController = navController,
                onPrimaryDebtorClicked = {},
                onPrimaryCreditorClicked = {},
                submitTransaction = ({ _, _, _ -> }),
            )
        }

        composeTestRule.onNodeWithText("Primary Debtor: Austin").assertExists()
        composeTestRule.onNodeWithText("Owes You: ").assertExists()
        composeTestRule.onNodeWithText("$10.00").assertExists()
    }

    @Test
    fun testPrimaryCreditor() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            SummaryScreen(
                repository = repository,
                navController = navController,
                onPrimaryDebtorClicked = {},
                onPrimaryCreditorClicked = {},
                submitTransaction = ({ _, _, _ -> }),
            )
        }

        composeTestRule.onNodeWithText("Primary Creditor: Bob").assertExists()
        composeTestRule.onNodeWithText("You Owe: ").assertExists()
        composeTestRule.onNodeWithText("$10.00").assertExists()
    }

    @Test
    fun testAddTransaction() {
        val onSubmitTransaction = { userID: Long, amount: Double, description: String ->
            assert(userID == 1.toLong())
            assert(amount == 12.34)
            assert(description == "Unit Test Transaction")
        }

        composeTestRule.setContent {
            val navController = rememberNavController()
            SummaryScreen(
                repository = repository,
                navController = navController,
                onPrimaryDebtorClicked = {},
                onPrimaryCreditorClicked = {},
                submitTransaction = onSubmitTransaction,
            )
        }

        composeTestRule.onNodeWithText("Add Transaction").assertExists()

        val dropdown = composeTestRule.onNodeWithTag("add_transaction_dropdown")
        dropdown.assertExists()
        dropdown.performClick()

        val amountField = composeTestRule.onNodeWithText("Amount")
        amountField.assertExists()
        amountField.performClick()
        amountField.performTextInput("12.34")

        val descriptionField = composeTestRule.onNodeWithText("Description")
        descriptionField.assertExists()
        descriptionField.performClick()
        descriptionField.performTextInput("Unit Test Transaction")

        val submitButton = composeTestRule.onNodeWithTag("add_transaction_submit_button")
        submitButton.assertExists()
        submitButton.performClick()
    }
}