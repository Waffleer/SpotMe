package com.example.spotme.ui

import android.icu.text.NumberFormat
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.ui.elements.NavButton
import com.example.spotme.viewmodels.SummaryViewModel
import kotlin.math.absoluteValue
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.draw.shadow
import com.example.spotme.ui.elements.AddProfileNavButton
import com.example.spotme.ui.elements.TestingNavButton
import com.example.spotme.ui.elements.ToDetailsNavButton
import com.example.spotme.ui.elements.ToSummaryNavButton
import androidx.compose.ui.graphics.Color
import com.example.spotme.ui.elements.details.AddTransactionCard

/**
 * Displays a summary of account information including:
 *  Overall Balance
 *  Who owes you the most money (Primary Debtor)
 *  Who you owe the most money to (Primary Creditor)
 *
 * And also allows you to submit transactions via a transaction card.
 *
 * @param repository the project's repository
 * @param onDetailsPressed lambda that navigates to the details screen.
 * @param onPlusPressed lambda that navigates to the add profile screen.
 * @param onPrimaryDebtorClicked lambda that navigates to the primary debtor's expanded profile.
 * @param onPrimaryCreditorClicked lambda that navigates to the primary creditor's expanded profile.
 * @param submitTransaction lambda that submits a transaction to the database.
 * @param modifier controls the form of the composable.
 */
@Composable
fun SummaryScreen(
    repository: RepositoryInterface,
    onPlusPressed: () -> Unit,
    onDetailsPressed: () -> Unit,
    onTestPressed: () -> Unit,
    onPrimaryDebtorClicked: (Long) -> Unit,
    onPrimaryCreditorClicked: (Long) -> Unit,
    submitTransaction: (Long, Double, String) -> Unit,
    modifier: Modifier = Modifier
    ) {
    val summaryViewModel by remember { mutableStateOf(SummaryViewModel(repository))}
    val totalBalance by summaryViewModel.totalBalance.collectAsState()
    val primaryDebtor by summaryViewModel.primaryDebtor.collectAsState()
    val primaryCreditor by summaryViewModel.primaryCreditor.collectAsState()
    val oldestDebt by summaryViewModel.oldestDebt.collectAsState()
    val everything by summaryViewModel.everything.collectAsState()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium))
                .weight(1f),
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    //containerColor = MaterialTheme.colorScheme.tertiaryContainer //could be used later if we figure out color schemes
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Column(modifier.padding(dimensionResource(R.dimen.padding_small))) {
                    Text(
                        stringResource(R.string.summary_balance),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = modifier
                    )
                    var color = MaterialTheme.colorScheme.onTertiaryContainer
                    if (totalBalance.totalBalance < 0) {
                        color = Color.Red
                    }
                    Text(
                        NumberFormat.getCurrencyInstance().format(totalBalance.totalBalance),
                        style = MaterialTheme.typography.headlineMedium,
                        color = color,
                        modifier = modifier
                    )
                }
            }

            DebtorItem( // Primary Debtor Card
                label = stringResource(R.string.summary_debtor),
                summaryViewModel = summaryViewModel,
                visitProfile = { onPrimaryDebtorClicked(primaryDebtor.largestDebtor.profileId!!) }
            )

            CreditorItem( // Primary Creditor Card
                label = stringResource(R.string.summary_creditor),
                summaryViewModel = summaryViewModel,
                visitProfile = { onPrimaryCreditorClicked(primaryCreditor.largestCreditor.profileId!!) }
            )
            /* REMOVED BECAUSE WE MADE DEBTS USELESS
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = modifier
                    .wrapContentHeight()
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth()
            ) {
                val formatter = SimpleDateFormat("dd MMM yyyy HH:mma")
                Column(modifier.padding(dimensionResource(R.dimen.padding_small))) {
                    Row(modifier) {
                        Text(stringResource(R.string.summary_oldest_date), style = MaterialTheme.typography.titleMedium)
                        Text(oldestDebt.oldestDebt.name)
                    }
                    Text(stringResource(R.string.summary_amount) + NumberFormat.getCurrencyInstance().format(oldestDebt.oldestDebt.totalDebt),
                        style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(R.string.summary_date) + formatter.format(oldestDebt.oldestDebt.createdDate),
                        style = MaterialTheme.typography.titleMedium)
                }
            }*/
            val names = everything.profilesWithEverything.map {
                Pair(
                    it.profile.name,
                    it.profile.profileId
                )
            }
            AddTransactionCard(
                names = names,
                submitTransaction = submitTransaction,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }

        //jank navbar
        Card(
            shape = RoundedCornerShape(16.dp), // Adjust the radius as needed
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
        ) {
        NavigationBar(
            content = {
                Row( //NavButtons
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    ToSummaryNavButton(
                        labelResourceId = R.string.home_button,
                        onClick = {  },
                        modifier = Modifier
                            .padding(4.dp)
                    )
                    AddProfileNavButton(
                        labelResourceId = R.string.add_profile,
                        onClick = {},
                        modifier = Modifier
                            .padding(4.dp)
                    )
                    ToDetailsNavButton(
                        labelResourceId = R.string.details,
                        onClick = { onDetailsPressed() },
                        modifier = Modifier
                            .padding(4.dp)
                    )
                    TestingNavButton(
                        labelResourceId = R.string.TestingScreen,
                        onClick = { onTestPressed() },
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            })//end of navbar
    }
    }
}

/**
 * Displays information about the profile that owes you the most money.
 * @param label Describes the significance of the balance information.
 * @param summaryViewModel The summary screen's view model
 * @param visitProfile lambda that navigates to the display profile's expanded profile screen.
 * @param modifier dictates the form of the composable.
 */
@Composable
fun DebtorItem(
    label: String,
    summaryViewModel: SummaryViewModel,
    modifier: Modifier = Modifier,
    visitProfile: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // state of dropdown
    val profile by summaryViewModel.primaryDebtor.collectAsState()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
            .clickable { visitProfile() }
    ) {
        Column(
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .animateContentSize( // This smooths out the expansion animation.
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row {
                Text(
                    label + profile.largestDebtor.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier
                        .weight(1F)
                        .padding(
                            dimensionResource(id = R.dimen.padding_very_small)
                        )
                )
                ProfileExpansionButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            if (expanded) {
                Text(stringResource(R.string.summary_payment_method) + profile.largestDebtor.paymentPreference,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
                Text(
                    profile.largestDebtor.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_very_small))
                    .fillMaxWidth()
            )
            {
                Row {
                    Text(
                        stringResource(R.string.summary_owes),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = modifier
                            .align(Alignment.CenterVertically)
                            .padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    var color = MaterialTheme.colorScheme.primaryContainer
                    //if (profile.largestDebtor.totalDebt > 0) { color = Color.Black }
                    Text(
                        text = NumberFormat.getCurrencyInstance().format(profile.largestDebtor.totalDebt),
                        style = MaterialTheme.typography.bodyLarge,
                        color = color,
                        modifier = modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }

    }
}
/**
 * Displays information about the profile that you owe the most money to.
 * @param label Describes the significance of the balance information.
 * @param summaryViewModel The summary screen's view model
 * @param visitProfile lambda that navigates to the display profile's expanded profile screen.
 * @param modifier dictates the form of the composable.
 */
@Composable
fun CreditorItem(
    label: String,
    summaryViewModel: SummaryViewModel,
    modifier: Modifier = Modifier,
    visitProfile: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // state of dropdown
    val profile by summaryViewModel.primaryCreditor.collectAsState()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
            .clickable { visitProfile() }
    ) {
        Column(
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .animateContentSize( // This smooths out the expansion animation.
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row {
                Text(
                    label + profile.largestCreditor.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier
                        .weight(1F)
                        .padding(
                            dimensionResource(id = R.dimen.padding_very_small)
                        )
                )
                ProfileExpansionButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = modifier
                        .size(25.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            if (expanded) {
                Text(
                    stringResource(R.string.summary_payment_method) + profile.largestCreditor.paymentPreference,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
                Text(
                    profile.largestCreditor.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                )
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_very_small))
                    .fillMaxWidth()
            )
            {
                Row {
                    Text(
                        stringResource(R.string.summary_owed),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = modifier
                            .align(Alignment.CenterVertically)
                            .padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    var color = MaterialTheme.colorScheme.primaryContainer
                    if (profile.largestCreditor.totalDebt < 0) { color = Color.Red }
                    Text(
                        NumberFormat.getCurrencyInstance().format(profile.largestCreditor.totalDebt.absoluteValue),
                        style = MaterialTheme.typography.bodyLarge,
                        color = color,
                        modifier = modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }

    }
}

/**
 * Button used to expand each profile card in order to display additional information.
 * @param expanded Whether the profile is currently expanded or not.
 * @param onClick Lambda that expands or contracting the profile.
 * @param modifier Defines the form of the composable.
 */
@Composable
private fun ProfileExpansionButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ){
        Icon(
            imageVector = if (expanded) Filled.KeyboardArrowDown else Filled.KeyboardArrowUp,
            contentDescription = "Expansion Button",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}