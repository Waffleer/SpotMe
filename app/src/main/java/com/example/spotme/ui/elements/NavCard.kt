package com.example.spotme.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotme.R
import com.example.spotme.SpotMeScreen

/**
 * Provides a consistent navigation interface
 *
 * @param navController the naviation controller
 * @param toSummaryScreen Lambda navigating to the summary screen
 * @param toDetailsScreen Lambda navigating to the details screen
 * @param toAddProfileScreen Lambda navigating to the add profile screen
 */
@Composable
fun NavCard(
    navController: NavController,
    toSummaryScreen: () -> Unit = {
        if (navController.currentBackStackEntry?.destination?.route != SpotMeScreen.Summary.name) {
            navController.navigate(SpotMeScreen.Summary.name)
        }
                                  },
    toDetailsScreen: () -> Unit = {
        if (navController.currentBackStackEntry?.destination?.route != SpotMeScreen.Details.name) {
            navController.navigate(SpotMeScreen.Details.name)
        }
                                  },
    toAddProfileScreen: () -> Unit = {
        if (navController.currentBackStackEntry?.destination?.route != SpotMeScreen.AddProfile.name) {
            navController.navigate(SpotMeScreen.AddProfile.name)
        }
                                     },
) {
    Card(
        shape = RoundedCornerShape(16.dp), // Adjust the radius as needed
        modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
    ) {
        NavigationBar(
            content = {
                Row( //NavButtons
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    ToSummaryNavButton(
                        labelResourceId = R.string.home_button,
                        onClick = { toSummaryScreen() },
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1.2f)
                    )
                    AddProfileNavButton(
                        labelResourceId = R.string.add_profile,
                        onClick = { toAddProfileScreen() },
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f)
                    )
                    ToDetailsNavButton(
                        labelResourceId = R.string.details,
                        onClick = { toDetailsScreen() },
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1.2f)
                    )
                    /*
                    TestingNavButton(
                        labelResourceId = R.string.TestingScreen,
                        onClick = { onTestPressed() },
                        modifier = Modifier
                            .padding(4.dp)
                    )*/
                }
            }
        )
    }
}