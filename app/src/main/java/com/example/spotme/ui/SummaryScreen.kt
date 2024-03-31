package com.example.spotme.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import com.example.spotme.database.Repository
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.ui.elements.NavButton
import com.example.spotme.viewmodels.LocalUiState
import com.example.spotme.viewmodels.SummaryViewModel

@Composable
fun SummaryScreen(
    repository: RepositoryInterface,
    onDetailsPressed: () -> Unit,
    onPlusPressed: () -> Unit,

    ) {
    val summaryViewModel = SummaryViewModel(repository)
    val summaryUiState by summaryViewModel.summaryUiModel.collectAsState()

    Text("I am supposed to be the summary screen")


    
    
    
    
    //Basic Nav Buttons
    Row( //NavButtons
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        NavButton(
            labelResourceId = R.string.details,
            onClick = { onDetailsPressed() },
            modifier = Modifier
                .padding(12.dp)
        )
        NavButton(
            labelResourceId = R.string.plus_button,
            onClick = { onPlusPressed() },
            modifier = Modifier
                .padding(12.dp)
        )

    }
}