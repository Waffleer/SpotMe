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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotme.data.Debt
import com.example.spotme.data.Profile
import com.example.spotme.data.Transaction
import java.util.Date


@Composable
fun AddDebtTransactionScreen(
    profile: Profile?,

    ) { if(profile == null){ Text("Profile is null, please fix") } else {

    Column {

        val debtNameState = remember { mutableStateOf("") }
        val debtDescriptionState = remember { mutableStateOf("") }
        val debtAmountState = remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Add Transaction to ${profile.name}")

            TextField(
                value = debtNameState.value,
                onValueChange = { debtNameState.value = it },
                label = { Text("Debt Name") }
            )

            TextField(
                value = debtDescriptionState.value,
                onValueChange = { debtDescriptionState.value = it },
                label = { Text("Debt Description") }
            )

            TextField(
                value = debtAmountState.value,
                onValueChange = { newText ->
                    val cleanString = newText.replace(Regex("[^\\d.]"), "")
                    val number = cleanString.toDoubleOrNull() ?: 0.0
                    debtAmountState.value = String.format("%.2f", number)
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
                        description = debtDescriptionState.value,
                        amount = debtAmountState.value.toDoubleOrNull() ?: 0.0,
                        canceled = false,
                        createdDate = Date(),
                        debtId = null,

                    )
                }
            ) {
                Text("Add Debt")
            }
        }
    }
}

    }
