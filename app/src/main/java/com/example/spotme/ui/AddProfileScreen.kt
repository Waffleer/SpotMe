package com.example.spotme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.spotme.data.PaymentType
import com.example.spotme.data.Profile
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.rememberCoroutineScope
import com.example.spotme.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun AddProfileScreen(
    viewModel: ProfileViewModel
) {
    val name = remember { mutableStateOf(TextFieldValue()) }
    val description = remember { mutableStateOf(TextFieldValue()) }
    val amount = remember { mutableStateOf(TextFieldValue()) }
    val paymentPreference = remember { mutableStateOf(TextFieldValue()) }


    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TextField inputs omitted for brevity

        Button(
            onClick = {
                coroutineScope.launch {


                    viewModel.insertProfile(
                        Profile(
                            id = null,
                            debts = null,
                            name = name.value.text,
                            description = description.value.text,
                            amount = amount.value.text.toDoubleOrNull() ?: 0.0,
                            paymentPreference = PaymentType.valueOf(paymentPreference.value.text)
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Profile")
        }
    }
}