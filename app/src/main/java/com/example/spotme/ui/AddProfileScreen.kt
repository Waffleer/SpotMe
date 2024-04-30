package com.example.spotme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.spotme.data.*
import com.example.spotme.viewmodels.ProfileViewModel

@Composable
fun AddProfileScreen(
    addProfileToDatabase: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var paymentPreference by remember { mutableStateOf(PaymentType.NONE) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Payment Preference")
        Column(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            val paymentTypes = PaymentType.entries
            for (i in paymentTypes.indices step 2) {
                val rowPaymentTypes = paymentTypes.slice(i until i + 2)
                Row {
                    for (paymentType in rowPaymentTypes) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            RadioButton(
                                selected = paymentPreference == paymentType,
                                onClick = { paymentPreference = paymentType }
                            )
                            Text(paymentType.title)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    addProfileToDatabase(name, description, paymentPreference.toString())
                    /*
                    val defaultDebt = Debt(
                        id = null,
                        userID = null,
                        name = "Default Debt",
                        description = "Default Debt Description",
                        transactions = emptyList(),
                        amount = 0.0,
                        canceled = false,
                        createdDate = Date(),
                        hidden = false
                    )

                    ProfileViewModel.insertProfile(
                        Profile(
                            id = null,
                            name = name,
                            description = description,
                            amount = 0.0,
                            debts = listOf(defaultDebt),
                            paymentPreference = paymentPreference
                        )
                    )*/
                    // Reset fields after insertion
                    name = ""
                    description = ""
                    paymentPreference = PaymentType.NONE
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Profile")
        }
    }
}