package com.example.spotme

import android.util.Log
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
import com.example.spotme.viewmodels.DetailsViewModel
import com.example.spotme.viewmodels.ExpandedProfileViewModel
import com.example.spotme.viewmodels.FilterType
import com.example.spotme.viewmodels.SpotMeViewModel


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
    val localDatabase = LocalDatabase.getInstance(LocalContext.current)
    val spotMeRepository = Repository.getRepository(localDatabase)
    val detailsViewModel: DetailsViewModel = DetailsViewModel(spotMeRepository)
    val expandedProfileViewModel by remember { mutableStateOf(ExpandedProfileViewModel(spotMeRepository))}

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
        val detailsCurrentProfile = StaticDataSource.profiles[0]
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
                    }
                )
            }

            composable(route = SpotMeScreen.Details.name) {
                DetailsScreen(
                    uiState = detailsUiState,
                    onSummeryPressed = {},
                    onProfilePressed = {
                        //detailsViewModel.setCurrentProfile(it)
                        expandedProfileViewModel.setCurrentProfileId(it)
                        navController.navigate(SpotMeScreen.ExpandedProfile.name)
                    },
                    onAddPressed = {
                        detailsViewModel.setCurrentProfile(it)
                        navController.navigate(SpotMeScreen.AddDebtTransaction.name)
                    },
                    onFilterAmountHighPressed = {
                        detailsViewModel.setFilterType(FilterType.AMOUNT_HIGH)
                    },
                    onFilterAmountLowPressed = {
                        detailsViewModel.setFilterType(FilterType.AMOUNT_LOW)
                    }
                ) {
                    detailsViewModel.whatisfiltertype()
                }
            }

            composable(route = SpotMeScreen.ExpandedProfile.name) {
                ExpandedProfileScreen(
                    //profile = detailsUiState.currentProfile
                    profile = detailsCurrentProfile,
                    expandedProfileViewModel = expandedProfileViewModel,
                )
            }

            composable(route = SpotMeScreen.AddDebtTransaction.name) {
                AddDebtTransactionScreen(
                    //profile = detailsUiState.currentProfile
                    profile = detailsCurrentProfile
                )
            }


            /* Add Navigation to other screens here like so:
            composable(route = SubShopScreen.Order.name) {

            }*/
        }
    }
}