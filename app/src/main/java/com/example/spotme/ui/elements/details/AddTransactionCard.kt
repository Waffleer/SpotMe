package com.example.spotme.ui.elements.details

import android.media.MediaPlayer
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
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotme.R


/**
 * Composable function to display a card for adding transactions.
 * Users can input transaction details such as amount, description, and select a user from a dropdown menu.
 *
 * @param names List of pairs containing user names and their corresponding IDs.
 * @param submitTransaction Lambda function to submit a transaction.
 * @param modifier Modifier for styling.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionCard(
    names: List<Pair<String, Long?>>,
    submitTransaction: (Long, Double, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val h: Long = 0
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var userId by remember { mutableStateOf(h) }
    val context = LocalContext.current

    //for the sounds
    val audioContext = LocalContext.current
    val audioPlayer = MediaPlayer.create(audioContext, R.raw.applepay)

    // Need to check because names is empty on initial compose
    if (!names.isEmpty()) {
        selectedText = names[0].first
        userId = names[0].second!!
    } else {
        selectedText = "placeholder"
    }

    val submitButtonLogic = {
        println("SUBMIT")
        println(amount)
        println(description)
        if (amount != "" && description != "") {
            submitTransaction(userId, amount.toDoubleOrNull() ?: 0.0, description)
            audioPlayer.start()
            amount = ""
            description = ""
            Toast.makeText(
                context,
                context.getText(R.string.transaction_submitted),
                Toast.LENGTH_SHORT
            ).show()
        } else if (amount == "") {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enter_amount),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enter_description),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Row {
                    Text(
                        text = stringResource(id = R.string.add_transaction),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold, // Set the fontWeight to FontWeight.Bold

                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.padding_small))
                            .weight(1F)

                    )

                    //TOP RIGHT BUTTON IMPLEMENTATION
                    Column {
                        IconButton(
                            onClick = {
                                submitButtonLogic()
                            },
                            modifier = Modifier
                                .align(Alignment.End)
                                .size(dimensionResource(R.dimen.submit_icon))
                                .testTag("add_transaction_submit_button")

                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = stringResource(R.string.create_button),
                                tint = MaterialTheme.colorScheme.secondary

                            )
                        }
                    }
                }

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
                        },
                        modifier = Modifier.testTag("add_transaction_dropdown")

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
                            onDismissRequest = { expanded = false },
                        ) {
                            names.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.first) },
                                    onClick = {
                                        selectedText = item.first
                                        userId = item.second!!
                                        expanded = false

                                        Toast.makeText(context, item.first, Toast.LENGTH_SHORT).show()

                                        Log.d("j_selectedText_changed", "userId: " + userId)
                                    }
                                )
                            }
                        }
                    }
                }//end of drop down menu

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_very_small))
                        .testTag("add_transaction_amount")
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
                        onDone = { submitButtonLogic() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_very_small))
                        .testTag("add_transaction_description")
                )
            }
        }
    }
}


@Composable
private fun AddTransactionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
        // modifier = Modifier.align(Alignment.End)

    ) {
        Icon(
            imageVector = Icons.Filled.Create,
            contentDescription = stringResource(R.string.create_button),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}