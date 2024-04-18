package com.example.spotme.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotme.R
import com.example.spotme.data.Debt
import com.example.spotme.data.Profile
import com.example.spotme.data.Transaction
import java.util.Date


@Composable
fun AddDebtTransactionScreen(
    profile: Profile?,

    ) { if(profile == null){ Text("Profile is null, please fix") } else {

    Column {

        val transactionNameState = remember { mutableStateOf("") }
        val transactionDescriptionState = remember { mutableStateOf("") }
        val transactionAmountState = remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.add_transaction_to)+"${profile.name}")

            TextField(
                value = transactionNameState.value,
                onValueChange = { transactionNameState.value = it },
                label = { Text(text = stringResource(R.string.transaction_name)) }
            )

            TextField(
                value = transactionDescriptionState.value,
                onValueChange = { transactionDescriptionState.value = it },
                label = { Text(text =  stringResource(R.string.transaction_description)) }
            )

            TextField(
                value = transactionAmountState.value,
                onValueChange = { newText ->
                        val cleanString = newText.replace(Regex("[^\\d.]"), "")
                        val numberRegex = Regex("^\\d*\\.?\\d{0,2}")
                        if (cleanString.matches(numberRegex)) {
                            transactionAmountState.value = cleanString
                        }
                },
                label = { Text("Debt Amount") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle the completion action if needed
                    }
                ),
                leadingIcon = { Text("$") }
            )

            Button(
                onClick = {
                    val newTransaction = Transaction(
                        id = null,
                        description = transactionDescriptionState.value,
                        amount = transactionAmountState.value.toDoubleOrNull() ?: 0.0,
                        canceled = false,
                        createdDate = Date(),
                        debtId = null,

                    )
                }
            ) {
                Text(text = stringResource(R.string.add_transaction))
            }
        }
    }
}

    }
