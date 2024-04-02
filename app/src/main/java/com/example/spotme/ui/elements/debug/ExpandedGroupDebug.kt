package com.example.spotme.ui.elements.debug

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.spotme.data.Group

@Composable
fun ExpandedGroupScreenDebug(
    group: Group,
    scrollable: Boolean = true
    ) {


    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "id: ${group.id}")
        Text(text = "name: ${group.name}")
        Text(text = "description: ${group.description}")
        Text(text = "transactions: ")
        Text(text = "   id: ${group.transactions.id}")
        Text(text = "   name: ${group.transactions.name}")
        Text(text = "   description: ${group.transactions.description}")
        Text(text = "   userID: ${group.transactions.userID}")
        group.transactions.transactions.forEach { trans ->
            Text(text = "       id: ${trans.id}")
            Text(text = "       amount: ${trans.amount}")
            Text(text = "       description: ${trans.description}")
            Text(text = "       canceled: ${trans.canceled}")
            Text(text = "       date: ${trans.date}")
        }
        Text(text = "hidden: ${group.hidden}")
        Text(text = "Debts: ")
        group.debts.forEach { debt ->
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
        Text(text = "profiles: ")
        Text(text = "---------------------------------------")
        group.profiles.forEach { 
            ExpandedProfileScreenDebug(profile = it)
            Text(text = "---------------------------------------")
        }
    }
}