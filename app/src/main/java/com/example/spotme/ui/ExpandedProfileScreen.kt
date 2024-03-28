package com.example.spotme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.spotme.data.Profile
import androidx.compose.material3.Text
import com.example.spotme.ui.elements.ExpandedProfile.ExpandedProfileScreenDebug
@Composable
fun ExpandedProfileScreen(
    profile: Profile?,

) { if(profile == null){ Text("Profile is null, please fix") } else{

    Column {
        Text(text = "Expanded Profile Screen Screen\n")
        ExpandedProfileScreenDebug(profile = profile)
    }








    }
}