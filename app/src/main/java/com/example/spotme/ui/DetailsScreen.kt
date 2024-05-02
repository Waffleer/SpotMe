package com.example.spotme.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotme.R
import com.example.spotme.data.Profile
import com.example.spotme.ui.elements.AddProfileNavButton
import com.example.spotme.ui.elements.NavButton
import com.example.spotme.ui.elements.NavCard
import com.example.spotme.ui.elements.TestingNavButton
import com.example.spotme.ui.elements.ToDetailsNavButton
import com.example.spotme.ui.elements.ToSummaryNavButton
import com.example.spotme.ui.elements.details.ProfileCard

import com.example.spotme.viewmodels.DetailsUiState
import com.example.spotme.viewmodels.DetailsProfiles

/**
 * Composable function to display the details screen.
 * This screen displays a list of profiles with filtering options.
 *
 * @param uiState ViewModel state for details screen.
 * @param navController NavController for navigation.
 * @param onProfilePressed Callback function for when a profile is pressed.
 * @param onAddPressed Callback function for when the add button is pressed.
 * @param onFilterAmountHighPressed Callback function for when the high amount filter button is pressed.
 * @param onFilterAmountLowPressed Callback function for when the low amount filter button is pressed.
 * @param modifier Modifier for styling.
 */

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    navController: NavController,
    onProfilePressed: (Long) -> Unit,
    onAddPressed: (Long) -> Unit,
    onFilterAmountHighPressed: () -> Unit,
    onFilterAmountLowPressed: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                //.padding(dimensionResource(R.dimen.padding_medium))
                .weight(1f),
        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // Center align items horizontally

        ) {
            // Button to filter profiles by high amount
            IconButton(
                onClick = { onFilterAmountHighPressed() },
                modifier = Modifier
                    .padding(top = 11.dp),
            ) {

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = stringResource(R.string.filter_amount_high),

                    )
            }
            // Button to filter profiles by low amount
            IconButton(
                onClick = { onFilterAmountLowPressed() },
                modifier = Modifier
                    .padding(top = 11.dp),
            ) {

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.filter_amount_low),
                    )
            }
        }
            // Display profiles with ProfileCard
            Column () {
            uiState.filterProfiles.forEach {
                ProfileCard(profile = it, onProfilePressed, onAddPressed)
            }


        }
        }
        //New Nav Bar
        NavCard(navController)
     }
}