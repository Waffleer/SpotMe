package com.example.spotme.ui.elements.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotme.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionCard(
    names: List<Pair<String, Long?>>,
    modifier: Modifier = Modifier,
    submitTransaction: (Long, Double, String) -> Unit
) {
    val h: Long = 0
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var userId by remember {mutableStateOf(h)}
    val context = LocalContext.current

    // Need to check because names is empty on initial compose
    if(!names.isEmpty()) {
        selectedText = names[0].first
        userId = names[0].second!!
    } else { selectedText = "placeholder" }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            modifier = Modifier
                //.fillMaxWidth()
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
Column {


    IconButton(
        onClick = {
            submitTransaction(userId,amount.toDouble(),description)
            amount = ""
            description = ""
                  }, //TODO make submit/create button work
        modifier = Modifier
            .align(Alignment.End)
            .size(25.dp)

    ) {
        Icon(
            imageVector = Icons.Filled.Create,
            contentDescription = stringResource(R.string.create_button),
            tint = MaterialTheme.colorScheme.secondary

        )
    }
}
                }
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_very_small))
                        //.height(16.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_very_small))
                        //.height(20.dp)

                )

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
                            names.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.first) },
                                    onClick = {
                                        selectedText = item.first
                                        expanded = false
                                        Toast.makeText(context, item.first, Toast.LENGTH_SHORT).show()
                                        userId = names.find { it.first.equals(item.first)}?.second!!
                                        Log.d("j_selectedText_changed", "userId: " + userId)
                                    }
                                )
                            }
                        }
                    }
                }
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
        //                    modifier = Modifier.align(Alignment.End)

    ) {
        Icon(
            imageVector = Icons.Filled.Create,
            contentDescription = stringResource(R.string.create_button),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}