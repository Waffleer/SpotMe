package com.example.spotme.ui


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import com.example.spotme.viewmodels.ExpandedProfileViewModel

@Composable
fun AddDebtTransactionScreen(
    expandedProfileViewModel: ExpandedProfileViewModel,
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

        Column {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.add_transaction_to)+" ${eProfile.name}")
            }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
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
                    onClick = {
                        submitButtonLogic()
                    }
                ) {
                    Text(stringResource(R.string.add_transaction))
                }
            }
        }
    }



