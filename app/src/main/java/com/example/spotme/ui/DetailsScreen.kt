package com.example.spotme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import com.example.spotme.data.Profile
import com.example.spotme.ui.elements.NavButton
import com.example.spotme.ui.elements.details.ProfileCard

import com.example.spotme.viewmodels.DetailsUiState
import com.example.spotme.viewmodels.DetailsProfiles


@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    //detailsProfiles: DetailsProfiles,
    onSummeryPressed: () -> Unit,
    onProfilePressed: (Profile) -> Unit,
    onAddPressed: (Profile) -> Unit,
    onFilterAmountHighPressed: () -> Unit,
    onFilterAmountLowPressed: () -> Unit,
    whatisfiltertype: () -> Unit,
    ) {
    Column {
        Text("I am supposed to be the Details screen")
        Column {
            uiState.filterProfiles.forEach{
                ProfileCard(profile = it, onProfilePressed, onAddPressed)
                //TODO update ProfileCard Function to make it look nice
                //TODO add + button
            }
        }
        NavButton(
            labelResourceId = R.string.filter_amount_high,
            onClick = { onFilterAmountHighPressed() },
            modifier = Modifier
                .padding(12.dp)
        )
        NavButton(
            labelResourceId = R.string.filter_amount_low,
            onClick = { onFilterAmountLowPressed() },
            modifier = Modifier
                .padding(12.dp)
        )
//        NavButton(
//            labelResourceId = R.string.filtertype,
//            onClick = { whatisfiltertype() },
//            modifier = Modifier
//                .padding(12.dp)
//        )
    }
}