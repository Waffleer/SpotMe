package com.example.spotme.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
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
    modifier: Modifier = Modifier
    ) {
    val summaryViewModel = SummaryViewModel(repository)
    val summaryUiState by summaryViewModel.summaryUiModel.collectAsState()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .weight(1f)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Column(modifier.padding(dimensionResource(R.dimen.padding_small))) {
                    Text("Balance:")
                    Text("$" + summaryUiState.totalBalance.toString(), modifier)
                }
            }
        }
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
}