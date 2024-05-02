package com.example.spotme.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotme.R
import com.example.spotme.data.*
import com.example.spotme.ui.elements.NavCard
import kotlinx.coroutines.launch

/**
 * Composable function to display a screen for adding a new profile.
 * Users can input profile details such as name, description, and preferred payment method.
 *
 * @param addProfileToDatabase Lambda function to add a profile to the database.
 * @param navController NavController for navigation.
 * @param postOpNavigation Lambda function for post-operation navigation.
 * @param modifier Modifier for styling.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProfileScreen(
    addProfileToDatabase: (String, String, String) -> Unit,
    navController: NavController,
    postOpNavigation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var paymentPreference by remember { mutableStateOf(PaymentType.NONE) }
    val paymentTypes = PaymentType.entries
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(1f),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                contentAlignment = Alignment.TopCenter
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .padding(top = 120.dp, start = 10.dp, end = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.AddProfileScreen),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold, // Set the fontWeight to FontWeight.Bold

                                modifier = Modifier
                                    .padding(start = dimensionResource(R.dimen.padding_small))
                                    .weight(1F)

                            )
                        }
                        Column() {

                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Name") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { } //submitButtonLogic() } //TODO HANDLE LATER
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.padding_very_small))
                            )
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Description") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { } //submitButtonLogic() } //TODO HANDLE LATER
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.padding_very_small))
                            )


                            val context = LocalContext.current
                            val dropdownHint = "Preferred Payment Method"

                            val coroutineScope = rememberCoroutineScope()


                            //DROP DOWN MENU
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.padding_small))
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = {
                                        expanded = !expanded
                                    }
                                ) {
                                    TextField(
                                        value = selectedText,
                                        onValueChange = {
                                            selectedText = it
                                        },
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
                                        modifier = Modifier.menuAnchor()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        Text(text = dropdownHint)
                                        paymentTypes.forEach { item ->
                                            DropdownMenuItem(
                                                text = { Text(text = item.name) },
                                                onClick = {
                                                    selectedText = item.name
                                                    paymentPreference = item // Update paymentPreference here
                                                    expanded = false
                                                    Toast.makeText(
                                                        context,
                                                        item.name,
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                    Log.d(
                                                        "j_selectedText_changed",
                                                        "selectedText: $selectedText"
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }//end of drop down menu


                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(dimensionResource(R.dimen.padding_small))
                            ) {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            addProfileToDatabase(
                                                name,
                                                description,
                                                paymentPreference.toString()
                                            )
                                            // Reset fields after insertion
                                            name = ""
                                            description = ""
                                            paymentPreference = PaymentType.NONE
                                            postOpNavigation()
                                        }
                                    },
                                    modifier = Modifier
                                        .size(25.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = stringResource(R.string.create_button),
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier
                                            .size(dimensionResource(R.dimen.large_icon_size))

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