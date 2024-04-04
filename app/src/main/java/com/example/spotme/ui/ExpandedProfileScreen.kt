package com.example.spotme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.spotme.data.Profile
import androidx.compose.material3.Text
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
import com.example.spotme.ui.elements.debug.ExpandedProfileScreenDebug

/**
 * Composable function to display an expanded profile screen.
 *
 * @param profile The profile to be displayed.
 * @param modifier [Modifier] to be applied to the layout.
 */
@Composable
fun ExpandedProfileScreen(
    profile: Profile?,
    modifier: Modifier = Modifier,
) {
    // Check if profile is null
    if (profile == null) {
        Text("Profile is null, please fix")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                .padding(top = dimensionResource(R.dimen.detail_card_list_padding_top)),
        ) {
            Text(
                text = profile.name,
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
                    profile.debts.forEach { debt ->
                        Text(
                            text = "${debt.name}",
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontSize = 17.sp,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(text = "${debt.description}\n")
                        debt.transactions.forEach { trans ->
                            Text(text = "$${trans.amount}",
                                modifier = Modifier,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(text = trans.description)
                            Text(text = "Canceled - ${trans.canceled}")
                            Text(text = "Transaction Made - ${trans.date}\n")
                        }
                    }
                }
            }

            //Text(text = "Expanded Profile Screen Screen\n")
            //ExpandedProfileScreenDebug(profile = profile)
        }
    }
}
