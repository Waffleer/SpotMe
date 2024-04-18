package com.example.spotme.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    onProfilePressed: (Long) -> Unit,
    onAddPressed: (Profile) -> Unit,
    onFilterAmountHighPressed: () -> Unit,
    onFilterAmountLowPressed: () -> Unit,
    whatisfiltertype: () -> Unit,
    ) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center // Center align items horizontally

        ) {
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
        Column (modifier = Modifier.verticalScroll(rememberScrollState())){
            uiState.filterProfiles.forEach {
                ProfileCard(profile = it, onProfilePressed, onAddPressed)
                //TODO update ProfileCard Function to make it look nice
                //TODO add + button
            }
        }
     }

}