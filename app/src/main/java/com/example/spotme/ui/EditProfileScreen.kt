package com.example.spotme.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import com.example.spotme.R
import com.example.spotme.data.PaymentType
import com.example.spotme.ui.elements.NavCard
import com.example.spotme.viewmodels.ExpandedProfileViewModel
/**
 * Composable function to display the edit profile screen.
 * Allows users to edit their profile details such as name, description, and payment preference.
 *
 * @param expandedProfileViewModel ViewModel containing the profile data.
 * @param editProfile Lambda function to edit the profile.
 * @param navigateBackToProfile Lambda function to navigate back to the profile screen.
 * @param navController NavController to handle navigation.
 * @param modifier Modifier for styling.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    expandedProfileViewModel: ExpandedProfileViewModel,
    editProfile: (Long, String, String, String) -> Unit,
    navigateBackToProfile: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Collecting profile data from ViewModel
    val profileEntity by expandedProfileViewModel.profileWithEverything.collectAsState()
    val eProfile = profileEntity.profileWithEverything.profile

    // Mutable state variables for user inputs
    val userId by remember { mutableStateOf(eProfile.profileId) }
    var name by remember { mutableStateOf(eProfile.name) }
    var description by remember { mutableStateOf(eProfile.description) }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(eProfile.paymentPreference) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                //.padding(dimensionResource(R.dimen.padding_medium))
                .weight(1f),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                contentAlignment = Alignment.TopCenter
            ) {
                // Card to contain profile editing fields
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
                            // Title for the edit profile screen
                            Text(
                                text = stringResource(id = R.string.EditProfileScreen),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = dimensionResource(R.dimen.padding_small))
                                    .weight(1F)
                            )
                        }

                        Column() {
                            // Input field for name
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
                            // Input field for description
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

                            // DropDown Prepopulation
                            val dropdownHint = "Preferred Payment Method"
                            val paymentTypes = PaymentType.entries

                            // Dropdown menu for selecting payment method
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
                                            // Dropdown menu items for each payment type
                                            DropdownMenuItem(
                                                text = { Text(text = item.name) },
                                                onClick = {
                                                    selectedText = item.name
                                                    expanded = false
                                                    Toast.makeText(
                                                        context,
                                                        item.name,
                                                        Toast.LENGTH_SHORT
                                                    ).show()
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

                            // Button to submit profile changes
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(dimensionResource(R.dimen.padding_small))
                            ) {
                                IconButton(
                                    onClick = {
                                        // Validation and submission logic
                                        if (name != "" && description != "") {
                                            editProfile(userId!!, name, description, selectedText)
                                            Toast.makeText(
                                                context,
                                                context.resources.getString(R.string.profile_edited),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navigateBackToProfile()
                                        } else if (name == "") {
                                            Toast.makeText(
                                                context,
                                                context.resources.getString(R.string.enter_name),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                context.resources.getString(R.string.enter_description),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    modifier = Modifier
                                        .size(25.dp)
                                ) {
                                    // Icon for the submit button
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
        // Navigation card to navigate back
        NavCard(navController)
    }
}
