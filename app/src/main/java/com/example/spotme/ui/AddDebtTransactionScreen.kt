package com.example.spotme.ui

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotme.R
import com.example.spotme.ui.elements.NavCard
import com.example.spotme.viewmodels.ExpandedProfileViewModel


@Composable
fun AddDebtTransactionScreen(
    expandedProfileViewModel: ExpandedProfileViewModel,
    navController: NavController,
    submitTransaction: (Long, Double, String) -> Unit
    ) {

    // ViewModel stuff
    val profileEntity by expandedProfileViewModel.profileWithEverything.collectAsState()
    val eProfile = profileEntity.profileWithEverything.profile
    // Local variables (removed name because that's not a field in our table)
    var transactionDescriptionState by remember { mutableStateOf("") }
    var transactionAmountState by remember { mutableStateOf("") }
    // Context
    val context = LocalContext.current

    val submitButtonLogic = { // For submitting transaction.
        if (transactionAmountState != "") {
            submitTransaction(
                eProfile.profileId!!,
                transactionAmountState.toDoubleOrNull() ?: 0.0,
                transactionDescriptionState
            )
            transactionAmountState = ""
            transactionDescriptionState = ""
            Toast.makeText(
                context,
                context.resources.getString(R.string.transaction_submitted),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enter_amount),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                //.padding(dimensionResource(R.dimen.padding_medium))
                .weight(1f),
        ) {

            Column {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .padding(top = 120.dp, start = 10.dp, end = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(R.dimen.padding_small)),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.add_transaction_to) + "\n${eProfile.name}",
                                textAlign = TextAlign.Center, // Center align the text
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold, // Set the fontWeight to FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextField(
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.padding_small))
                                    .clip(RoundedCornerShape(15.dp)),
                                value = transactionDescriptionState,
                                onValueChange = { transactionDescriptionState = it },
                                label = { Text(stringResource(R.string.transaction_description)) }
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextField(
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.padding_small))
                                    .clip(RoundedCornerShape(15.dp)),
                                value = transactionAmountState,
                                onValueChange = {
                                    transactionAmountState = it
                                    // HAD TO COMMENT THIS OUT BECAUSE
                                    // it wouldn't accept the negative sign
                                    /*newText ->
                            val cleanString = newText.replace(Regex("[^\\d.]"), "")
                            val numberRegex = Regex("^\\d*\\.?\\d{0,2}")
                            if (cleanString.matches(numberRegex)) {
                                transactionAmountState = cleanString
                            }*/
                                },
                                label = { Text("Debt Amount") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        submitButtonLogic()
                                    }
                                ),
                                leadingIcon = { Text("$") }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(bottom = dimensionResource(R.dimen.padding_medium)),
                            onClick = {
                                submitButtonLogic()
                            }
                        ) {
                            Text(stringResource(R.string.add_transaction))
                        }
                    }
                }
            }
        }
        NavCard(navController)
    }
}


