package com.example.spotme.ui

import android.content.Context
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spotme.data.DebugData
import com.example.spotme.database.LocalDatabase
import com.example.spotme.database.Repository
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.viewmodels.ExpandedProfileViewModel
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
class ExpandedProfileUITest {
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
        val viewModel = ExpandedProfileViewModel(repository);
        viewModel.setCurrentProfileId(1);

        composeTestRule.setContent {
            val navController = rememberNavController()
            ExpandedProfileScreen(
                expandedProfileViewModel = viewModel,
                navController = navController,
                onEditProfilePressed = {},
            )
        }

        composeTestRule.onNodeWithText("Austin").assertExists()
        composeTestRule.onNodeWithText("Loves apples").assertExists()
        composeTestRule.onNodeWithText("Prefers NONE").assertExists()
        composeTestRule.onNodeWithText("Walmart Trip").assertExists()
        composeTestRule.onNodeWithText("Box of apples").assertExists()
        composeTestRule.onNodeWithText("Container of apple sauce").assertExists()
        composeTestRule.onAllNodesWithText("$10.00").assertCountEquals(2)
        composeTestRule.onAllNodesWithText("Status: Ongoing").assertCountEquals(2)

        val editButton = composeTestRule.onNodeWithTag("expanded_profile_edit_button")
        editButton.assertExists()
        editButton.performClick()
    }
}