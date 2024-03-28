package com.example.spotme.ui.elements.ExpandedProfile

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.spotme.data.Profile

@Composable
fun ExpandedProfileScreenDebug(
    profile: Profile,

    ) {
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "id: ${profile.id}")
        Text(text = "name: ${profile.name}")
        Text(text = "bio: ${profile.description}")
        Text(text = "profileImage: ${profile.profileImage}")
        Text(text = "createDate: ${profile.createdDate}")
        Text(text = "paymentPreferences: ${profile.paymentPreference}")
        Text(text = "Debts: ")
        profile.debts.forEach { debt ->
            Text(text = "   id: ${debt.id}")
            Text(text = "   name: ${debt.name}")
            Text(text = "   description: ${debt.description}")
            Text(text = "   userID: ${debt.userID}")
            debt.transactions.forEach { trans ->
                Text(text = "       id: ${trans.id}")
                Text(text = "       amount: ${trans.amount}")
                Text(text = "       description: ${trans.description}")
                Text(text = "       canceled: ${trans.canceled}")
                Text(text = "       date: ${trans.date}")
            }
        }
    }
}