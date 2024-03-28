package com.example.spotme.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.spotme.ui.elements.ProfileCard
import com.example.spotme.viewmodels.DetailsUiState

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    onSummeryPressed: () -> Unit,
    onProfilePressed: () -> Unit,
    onAddPressed: () -> Unit,


    ) {
    Text("I am supposed to be the Details screen")
    ProfileCard(profile = uiState.profiles[0])



}