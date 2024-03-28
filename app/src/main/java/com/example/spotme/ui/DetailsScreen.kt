package com.example.spotme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.spotme.data.Profile
import com.example.spotme.ui.elements.details.ProfileCard
import com.example.spotme.viewmodels.DetailsUiState

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    onSummeryPressed: () -> Unit,
    onProfilePressed: (Profile) -> Unit,
    onAddPressed: (Profile) -> Unit,


    ) {

    Column {
        Text("I am supposed to be the Details screen")
        Column {
            uiState.profiles.forEach{
                ProfileCard(profile = it, onProfilePressed, onAddPressed)
                //TODO update ProfileCard Function to make it look nice
                //TODO add + button
            }
        }
    }



}