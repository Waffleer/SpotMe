package com.example.spotme.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.spotme.data.Profile
import com.example.spotme.ui.elements.debug.ExpandedProfileScreenDebug

@Composable
fun AddDebtTransactionScreen(
    profile: Profile?,

    ) { if(profile == null){ Text("Profile is null, please fix") } else {

    Column {
        Text(text = "Add Debt Transaction Screen\n")
        ExpandedProfileScreenDebug(profile = profile)
    }


    }
}