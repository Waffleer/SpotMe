package com.example.spotme.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.spotme.data.Profile
import com.example.spotme.ui.elements.ExpandedProfile.ExpandedProfileScreenDebug
import com.example.spotme.ui.elements.details.ProfileCard
import com.example.spotme.viewmodels.DetailsUiState

@Composable
fun AddDebtScreen(
    profile: Profile?,

    ) { if(profile == null){ Text("Profile is null, please fix") } else {

    Column {
        Text(text = "Add Debt Screen\n")
        ExpandedProfileScreenDebug(profile = profile)
    }


    }
}