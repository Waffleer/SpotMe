package com.example.spotme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import com.example.spotme.data.Group
import com.example.spotme.ui.elements.debug.ExpandedGroupScreenDebug

@Composable
fun ExpandedGroupScreen(
    group: Group?,

    ) { if(group == null){ Text("Group is null, please fix") } else{

    Column {
        Text(text = "Expanded Group Screen Screen\n")
        ExpandedGroupScreenDebug(group = group)
    }







    }
}