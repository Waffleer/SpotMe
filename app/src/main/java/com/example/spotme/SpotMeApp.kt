package com.example.spotme

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spotme.data.StaticDataSource
import com.example.spotme.database.LocalDatabase
import com.example.spotme.database.Repository
import com.example.spotme.ui.AddDebtTransactionScreen
import com.example.spotme.ui.DetailsScreen
import com.example.spotme.ui.ExpandedProfileScreen
import com.example.spotme.ui.SummaryScreen
import com.example.spotme.ui.TestingScreen
import com.example.spotme.viewmodels.DBProfileViewModel
import com.example.spotme.viewmodels.DBTransactionViewModel
import com.example.spotme.viewmodels.DetailsViewModel
import com.example.spotme.viewmodels.ExpandedProfileViewModel
import com.example.spotme.viewmodels.FilterType
import com.example.spotme.viewmodels.SpotMeViewModel
import kotlinx.coroutines.launch


/**
 * An Enum defining each screen alongside it's title.
 *
 * @param title the screen's title
 */
enum class SpotMeScreen(@StringRes val title: Int) {
    Summary(title = R.string.summary_header),
    Details(title = R.string.details_screen),
    ExpandedProfile(title = R.string.expanded_profile_screen),
    AddDebtTransaction(title = R.string.add_debt_transaction),
    TestingScreen(title = R.string.TestingScreen)
    // TODO add other screens here
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 *
 * @param currentScreen the current screen that the app is displaying
 * @param canNavigateBack flag denoting whether it's possible to go back
 * @param navigateUp lambda function for going back a screen
 * @param modifier modifies the form of this composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotMeAppBar(
    currentScreen: SpotMeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = modifier.testTag("BACK_BUTTON")
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        }
    )

}

/**
 * Runs the app and sets up screen navigation
 *
 * @param localViewModel the intermediary between the UI and data layers
 * @param navController manages navigation between screen locations
 */
@Composable
fun SpotMeApp(
    localViewModel: SpotMeViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    //get current backstack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // get name of the current screen, if null, set to welcome screen:
    val currentScreen = SpotMeScreen.valueOf(
        backStackEntry?.destination?.route ?: SpotMeScreen.Summary.name
    )

    // Instantiate the database, repo, and database view model
    val coroutineScope = rememberCoroutineScope()
    val localDatabase = LocalDatabase.getInstance(LocalContext.current)
    val spotMeRepository = Repository.getRepository(localDatabase)
    // <--------------------------------------------------------->
    val detailsViewModel: DetailsViewModel = DetailsViewModel(spotMeRepository)
    val expandedProfileViewModel by remember { mutableStateOf(ExpandedProfileViewModel(spotMeRepository))}
    val profileEntity by expandedProfileViewModel.profileWithEverything.collectAsState()
    val dbProfileViewModel: DBProfileViewModel = DBProfileViewModel(spotMeRepository)
    val dbTransactionViewModel: DBTransactionViewModel = DBTransactionViewModel(spotMeRepository, updateProfile_ = { pid: Long, amount: Double ->
        coroutineScope.launch {//Passing though the edit amount from dbProfileViewModel
            dbProfileViewModel.editProfileAmount(pid, amount)
        }
    }, updateDebt_ = {did: Long, amount: Double ->
        coroutineScope.launch {//Passing though the edit amount from dbProfileViewModel
            dbProfileViewModel.editDebtAmount(did, amount)
        }
    })

    Scaffold ( // Used to hold the app bar
        topBar = {
            SpotMeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = (navController.previousBackStackEntry != null && (currentScreen != (SpotMeScreen.Summary)) ),
                navigateUp = {
                    navController.popBackStack() //This sends you to the last screen
                    //navController.navigate(SpotMeScreen.Summary.name) - what it was before
                }
            )
        }
    ){ innerPadding ->
        // Local UI State from SpotMeViewModel/LocalUiState
        val detailsUiState by detailsViewModel.uiState.collectAsState()
        val detailsProfiles by detailsViewModel.profilesFlow.collectAsState() //Needs to initilize the stateflow for my sorting, i hate that this is necessary
        val profileState by dbProfileViewModel.uiState.collectAsState()
        val detailsCurrentProfile = StaticDataSource.profiles[0]

        // <----- Submit Transaction to Database lambda ----->
        val context = LocalContext.current
        // Submits transaction to database
        val submitTransactionToDatabase: (Long, Double, String) -> Unit = { userId, amount, description ->
            coroutineScope.launch {
                dbTransactionViewModel.createTransaction(userId, amount, description)
            }
        }
        // <------------------------------------------------->

        // ExpandedProfileScreen Stuff
        NavHost(
            navController = navController,
            startDestination = SpotMeScreen.Summary.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = SpotMeScreen.Summary.name) {
                SummaryScreen(
                    repository = spotMeRepository,
                    onDetailsPressed = {
                        navController.navigate(SpotMeScreen.Details.name)
                    },
                    onPlusPressed = {},
                    onPrimaryCreditorClicked = {
                        expandedProfileViewModel.setCurrentProfileId(it)
                        Log.d("x_primaryCreditorClicked","profileId: " + it.toString())
                        navController.navigate(SpotMeScreen.ExpandedProfile.name)
                                               },
                    onPrimaryDebtorClicked = {
                        expandedProfileViewModel.setCurrentProfileId(it)
                        Log.d("x_primaryDebtorClicked","profileId: " + it.toString())
                        navController.navigate(SpotMeScreen.ExpandedProfile.name)
                    },
                    onTestPressed = {
                        navController.navigate(SpotMeScreen.TestingScreen.name)
                    },
                    submitTransaction = submitTransactionToDatabase
                )
            }

            composable(route = SpotMeScreen.Details.name) {
                DetailsScreen(
                    uiState = detailsUiState,
                    onSummeryPressed = {},
                    onProfilePressed = {
                        expandedProfileViewModel.setCurrentProfileId(it)
                        navController.navigate(SpotMeScreen.ExpandedProfile.name)
                    },
                    onAddPressed = {
                        expandedProfileViewModel.setCurrentProfileId(it)
                        navController.navigate(SpotMeScreen.AddDebtTransaction.name)
                    },
                    onFilterAmountHighPressed = {
                        detailsViewModel.setFilterType(FilterType.AMOUNT_HIGH)
                    },
                    onFilterAmountLowPressed = {
                        detailsViewModel.setFilterType(FilterType.AMOUNT_LOW)
                    }
                )
            }

            composable(route = SpotMeScreen.ExpandedProfile.name) {
                ExpandedProfileScreen(
                    expandedProfileViewModel = expandedProfileViewModel,
                )
            }

            composable(route = SpotMeScreen.AddDebtTransaction.name) {
                AddDebtTransactionScreen(
                    expandedProfileViewModel = expandedProfileViewModel,
                    submitTransaction = submitTransactionToDatabase
                )
            }

            composable(route = SpotMeScreen.TestingScreen.name) {
                TestingScreen(
                    //profile = detailsUiState.currentProfile
                    uiState = profileState,
                    onT1Pressed = { name, description, payment ->
                        coroutineScope.launch {
                            dbProfileViewModel.createProfile(name, description, payment)
                        }
                    },
                    onT2Pressed = {id ->
                        coroutineScope.launch {
                            dbProfileViewModel.removeProfileById(id)
                        }
                    },
                    onT3Pressed = { profileID, amount, description ->
                        coroutineScope.launch {
                            dbTransactionViewModel.createTransaction(profileID, amount, description)
                        }
                    },
                    onT4Pressed = {tid ->
                        coroutineScope.launch {
                            dbTransactionViewModel.removeTransactionById(tid)
                        }
                    },
                )
            }


            /* Add Navigation to other screens here like so:
            composable(route = SubShopScreen.Order.name) {

            }*/
        }
    }
}