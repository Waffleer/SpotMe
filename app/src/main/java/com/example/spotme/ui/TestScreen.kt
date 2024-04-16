package com.example.spotme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import com.example.spotme.data.PaymentType
import com.example.spotme.data.Profile
import com.example.spotme.ui.elements.NavButton
import com.example.spotme.ui.elements.details.ProfileCard

import com.example.spotme.viewmodels.DetailsUiState
import com.example.spotme.viewmodels.DetailsProfiles
import com.example.spotme.viewmodels.ProfileState


@Composable
fun TestingScreen(
    uiState: ProfileState,

    onT1Pressed: (name: String, description: String, payment: PaymentType) -> Unit,
    onT2Pressed: (id: Long) -> Unit,
    onT3Pressed: (profileID: Long, amount: Double, description: String) -> Unit,
    onT4Pressed: (tid: Long) -> Unit,

) {
    Column {
        Text("I am supposed to be the Test screen")


        Row {
            NavButton(
                labelResourceId = R.string.add_transaction,
                onClick = {
                    onT3Pressed(
                        2,
                        100.0,
                        "Transaction description"
                    )
                },
                modifier = Modifier
                    .padding(12.dp)
            )
            NavButton(
                labelResourceId = R.string.remove_transaction,
                onClick = {
                    onT4Pressed(
                        89
                    )
                },
                modifier = Modifier
                    .padding(12.dp)
            )
        }

        Row {
            NavButton(
                labelResourceId = R.string.add_profile,
                onClick = {
                    onT1Pressed(
                        "Name Of Person3",
                        "This is your description",
                        PaymentType.NONE
                    )
                },
                modifier = Modifier
                    .padding(12.dp)
            )
            NavButton(
                labelResourceId = R.string.remove_profile,
                onClick = { onT2Pressed(3) },
                modifier = Modifier
                    .padding(12.dp)
            )
        }




//        NavButton(
//            labelResourceId = R.string.filtertype,
//            onClick = { whatisfiltertype() },
//            modifier = Modifier
//                .padding(12.dp)
//        )
    }
}