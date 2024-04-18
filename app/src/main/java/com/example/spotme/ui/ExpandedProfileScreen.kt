package com.example.spotme.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.spotme.data.Profile
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotme.R
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.ui.elements.debug.ExpandedProfileScreenDebug
import com.example.spotme.viewmodels.DetailsViewModel
import com.example.spotme.viewmodels.ExpandedProfileUIState
import com.example.spotme.viewmodels.ExpandedProfileViewModel
import com.example.spotme.viewmodels.ProfileEntity

/**
 * Composable function to display an expanded profile screen.
 *
 * @param profile The profile to be displayed.
 * @param modifier [Modifier] to be applied to the layout.
 */
@Composable
fun ExpandedProfileScreen(
    profile: Profile?,
    expandedProfileViewModel: ExpandedProfileViewModel,
    modifier: Modifier = Modifier,
) {
    val profileEntity by expandedProfileViewModel.profileWithEverything.collectAsState()
    val eProfile = profileEntity.profileWithEverything.profile
    val eDebts = profileEntity.profileWithEverything.debtsWithTransactions

    // ALL OF THE OLD CODE IS COMMENTED BELOW | Nothing is deleted
    // All I did was replace the profile stuff with eProfile and eDebt

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
            .padding(top = dimensionResource(R.dimen.detail_card_list_padding_top))
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = eProfile.name,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.memberSince),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 10.sp,
            color = Color.LightGray,
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
                Text(
                    text = stringResource(id = R.string.about),
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
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
                    Text(
                        text = "${debt.debt.name}",
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline
                    )
                    Text(text = "${debt.debt.description}\n")
                    debt.transactions?.forEach { trans ->
                        Text(
                            text = "$${trans.amount}",
                            modifier = Modifier,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(text = trans.description)
                        Text(text = "Canceled - ${trans.canceled}")
                        Text(text = "Transaction Made - ${trans.createdDate}\n")
                    }
                }
            }
        }


        /*
    // Check if profile is null
    if (profile == null) {
        Text("Profile is null, please fix")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                .padding(top = dimensionResource(R.dimen.detail_card_list_padding_top))
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = profileEntity.profileWithEverything.profile.name,//profile.name,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.memberSince) + profile.createdDate,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 10.sp,
                color = Color.LightGray,
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
                    Text(
                        text = stringResource(id = R.string.about),
                        modifier = Modifier.padding(bottom = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${profile.description}\n" +
                                "Prefers ${profile.paymentPreference}" //TODO could use a venmo or paypal logo once we have the data
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
                    profile.debts?.forEach { debt ->
                        Text(
                            text = "${debt.name}",
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontSize = 17.sp,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(text = "${debt.description}\n")
                        debt.transactions?.forEach { trans ->
                            Text(text = "$${trans.amount}",
                                modifier = Modifier,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(text = trans.description)
                            Text(text = "Canceled - ${trans.canceled}")
                            Text(text = "Transaction Made - ${trans.createdDate}\n")
                        }
                    }
                }
            }

            //Text(text = "Expanded Profile Screen Screen\n")
            //ExpandedProfileScreenDebug(profile = profile)
        }*/
    }
}
