package com.example.spotme.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.ui.elements.NavButton
import com.example.spotme.viewmodels.SummaryViewModel
import kotlin.math.absoluteValue

@Composable
fun SummaryScreen(
    repository: RepositoryInterface,
    onDetailsPressed: () -> Unit,
    onPlusPressed: () -> Unit,
    onPrimaryDebtorClicked: (Long) -> Unit,
    onPrimaryCreditorClicked: (Long) -> Unit,
    modifier: Modifier = Modifier
    ) {
    val summaryViewModel = SummaryViewModel(repository)
    val profilesWithDebts by summaryViewModel.profilesWithDebts.collectAsState()
    val totalBalance by summaryViewModel.totalBalance.collectAsState()
    val primaryDebtor by summaryViewModel.primaryDebtor.collectAsState()
    val primaryCreditor by summaryViewModel.primaryCreditor.collectAsState()
    val oldestDebt by summaryViewModel.oldestDebt.collectAsState()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .weight(1f),
            //verticalArrangement = Arrangement.Center
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
                    Text("Overall Balance: ",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = modifier
                    )
                    Text("$" + totalBalance.totalBalance.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = modifier
                    )
                }
            }
            Row(){
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    modifier = modifier
                        .wrapContentHeight()
                        .padding(dimensionResource(R.dimen.padding_small))
                        .weight(1f)
                        .clickable { onPrimaryDebtorClicked(primaryDebtor.largestDebtor.profileId!!) }
                ) {
                    Column(modifier.padding(dimensionResource(R.dimen.padding_small))) {
                        Text("Primary Debtor:", style = MaterialTheme.typography.titleMedium)
                        Text(primaryDebtor.largestDebtor.name)
                        Text("Owes You: ")
                        Text("$" + primaryDebtor.largestDebtor.totalDebt.absoluteValue)
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    modifier = modifier
                        .wrapContentHeight()
                        .padding(dimensionResource(R.dimen.padding_small))
                        .weight(1f)
                        .clickable { onPrimaryCreditorClicked(primaryCreditor.largestCreditor.profileId!!) }
                ) {
                    Column(modifier.padding(dimensionResource(R.dimen.padding_small))) {
                        Text("Primary Creditor:", style = MaterialTheme.typography.titleMedium)
                        Text(primaryCreditor.largestCreditor.name)
                        Text("You Owe: ")
                        Text("$" + primaryCreditor.largestCreditor.totalDebt.absoluteValue)
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                modifier = modifier
                    .wrapContentHeight()
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth()
            ) {
                Column(modifier.padding(dimensionResource(R.dimen.padding_small))) {
                    Row(modifier) {
                        Text("Oldest Debt: ", style = MaterialTheme.typography.titleMedium)
                        Text(oldestDebt.oldestDebt.name)
                    }
                    Text("Amount: $" + oldestDebt.oldestDebt.totalDebt.toString(),
                        style = MaterialTheme.typography.titleMedium)
                    Text("Date: " + oldestDebt.oldestDebt.createdDate.toString(),
                        style = MaterialTheme.typography.titleMedium )
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