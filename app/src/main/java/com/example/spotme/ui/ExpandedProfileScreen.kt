package com.example.spotme.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotme.R
import com.example.spotme.ui.elements.NavCard
import com.example.spotme.ui.elements.ToEditProfileNavButton
import com.example.spotme.viewmodels.ExpandedProfileViewModel
import java.text.SimpleDateFormat

/**
 * Composable function to display an expanded profile screen.
 *
 * @param expandedProfileViewModel the view model for the expanded profile screen.
 * @param onEditProfilePressed navigates to the edit profile screen.
 * @param modifier [Modifier] to be applied to the layout.
 */
@SuppressLint("SimpleDateFormat")
@Composable
fun ExpandedProfileScreen(
    expandedProfileViewModel: ExpandedProfileViewModel,
    navController: NavController,
    onEditProfilePressed: () -> Unit,
    modifier: Modifier = Modifier,
    ) {
    val profileEntity by expandedProfileViewModel.profileWithEverything.collectAsState()
    val eProfile = profileEntity.profileWithEverything.profile
    val eDebts = profileEntity.profileWithEverything.debtsWithTransactions

    // ALL OF THE OLD CODE IS COMMENTED BELOW | Nothing is deleted
    // All I did was replace the profile stuff with eProfile and eDebt

    Column() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimensionResource(R.dimen.detail_card_list_padding_top))
                .verticalScroll(rememberScrollState())
                .weight(1f),
        ) {
            Text(
                text = eProfile.name,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            val formatter2 = SimpleDateFormat("MMM dd yyyy")
            Text(
                text = stringResource(id = R.string.profileCreated) + " ${formatter2.format(eProfile.createdDate)}",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 16.sp,
                color = Color.Gray,
            )

            // Display about information
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.about),
                            modifier = Modifier,
                            //.padding(bottom = 16.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                        ToEditProfileNavButton(
                            labelResourceId = R.string.edit_profile,
                            onClick = { onEditProfilePressed() }, //TODO make button work
                            modifier = Modifier
                            //.padding(4.dp)
                        )
                    }
                    Text(
                        text = "${eProfile.description}\n" +
                                "Prefers ${eProfile.paymentPreference}" //TODO could use a venmo or paypal logo once we have the data
                    )
                }
            }

            // Display debts and transactions
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.debtsTrans),
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )

                    // Display each debt and its transactions
                    eDebts.forEach { debt ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(16.dp)),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = debt.debt.name,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    textDecoration = TextDecoration.Underline
                                )
                                if (debt.debt.description != "") {
                                    Text(text = "${debt.debt.description}\n")
                                }
                                debt.transactions.forEach { trans ->
                                    val statusText = if (trans.canceled) {
                                        "Canceled"
                                    } else {
                                        "Ongoing"
                                    }

                                    Text(
                                        text = "$${trans.amount}",
                                        modifier = Modifier,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(text = trans.description)
                                    Text(text = "Status: $statusText")
                                    val formatter1 = SimpleDateFormat("MMM dd yyyy HH:mma")
                                    Text(text = formatter1.format(trans.createdDate))

                                    Divider(
                                        color = Color.Gray,
                                        thickness = 1.dp,
                                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
        NavCard(navController)
    }
}
