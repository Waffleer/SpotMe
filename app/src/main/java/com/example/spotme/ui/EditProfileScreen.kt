package com.example.spotme.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.spotme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(

) {
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val h: Long = 0

    var userId by remember {mutableStateOf(h)}


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
                        text = stringResource(id = R.string.EditProfileScreen),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold, // Set the fontWeight to FontWeight.Bold

                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.padding_small))
                            .weight(1F)

                    )
                }
                Column(){

                    OutlinedTextField(
                        value = description, //TODO change all of this stuff to NAME
                        onValueChange = { description = it },
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

                    val prefers = listOf(
                        "Cash",
                        "Venmo",
                        "PayPal",
                        "Cash App",
                        "Apple Pay",
                        "Google Pay",
                        "Zelle",
                    )
                    val dropdownHint = "Preferred Payment Method"

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
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                Text(text = dropdownHint)
                                prefers.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            selectedText = item
                                            expanded = false
                                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                            Log.d("j_selectedText_changed", "selectedText: $selectedText")
                                        }
                                    )
                                }
                            }
                        }
                    }//end of drop down menu


                    // BOTTOM BUTTON IMPLEMENTATION
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    IconButton(
                        onClick = {
//                            if (amount != "" && description != "") {
//                                submitTransaction(userId, amount.toDouble(), description)
//                                amount = ""
//                                description = ""
//                            } else if (amount == ""){
//                                Toast.makeText(context, context.resources.getString(R.string.enter_amount), Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(context, context.resources.getString(R.string.enter_description), Toast.LENGTH_SHORT).show()
//                            }
                        },
                        modifier = Modifier
                            //.align(Alignment.End)
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