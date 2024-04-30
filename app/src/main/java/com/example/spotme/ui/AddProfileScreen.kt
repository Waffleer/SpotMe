package com.example.spotme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import kotlinx.coroutines.launch
import com.example.spotme.data.*
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.viewmodels.ProfileViewModel
import java.util.Date

@Composable
fun AddProfileScreen(
    profileViewModel: ProfileViewModel.Companion,
    //repository: RepositoryInterface,
    modifier: Modifier = Modifier,
) {
    val name = remember { mutableStateOf(TextFieldValue()) }
    val description = remember { mutableStateOf(TextFieldValue()) }
    val paymentPreference = remember { mutableStateOf(PaymentType.NONE) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Payment Preference")
        Column(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            val paymentTypes = PaymentType.values()
            for (i in paymentTypes.indices step 2) {
                val rowPaymentTypes = paymentTypes.slice(i until i + 2)
                Row {
                    for (paymentType in rowPaymentTypes) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            RadioButton(
                                selected = paymentPreference.value == paymentType,
                                onClick = { paymentPreference.value = paymentType }
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
                            name = name.value.text,
                            description = description.value.text,
                            amount = 0.0,
                            debts = listOf(defaultDebt),
                            paymentPreference = paymentPreference.value
                        )
                    )
                    // Reset fields after insertion
                    name.value = TextFieldValue()
                    description.value = TextFieldValue()
                    paymentPreference.value = PaymentType.NONE
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Profile")
        }
    }
}