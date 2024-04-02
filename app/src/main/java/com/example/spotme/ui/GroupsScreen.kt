package com.example.spotme.ui
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import com.example.spotme.data.Group
//import com.example.spotme.data.Profile
//import com.example.spotme.ui.elements.details.ProfileCard
//import com.example.spotme.ui.elements.groups.GroupCard
//import com.example.spotme.viewmodels.DetailsUiState
//import com.example.spotme.viewmodels.GroupsUiState
//
//@Composable
//fun GroupsScreen(
//    uiState: GroupsUiState,
//    onSummeryPressed: () -> Unit,
//    onGroupPressed: (Group) -> Unit,
//    onAddTransactionPressed: (Group) -> Unit,
//
//
//    ) {
//
//    Column {
//        Text("I am supposed to be the Details screen")
//        Column {
//            uiState.groups.forEach{
//                GroupCard(group = it, onGroupPressed, onAddTransactionPressed)
//                //TODO update ProfileCard Function to make it look nice
//                //TODO add + button
//            }
//        }
//    }
//
//
//
//}