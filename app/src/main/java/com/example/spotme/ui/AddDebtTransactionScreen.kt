package com.example.spotme.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotme.data.Profile
import com.example.spotme.ui.elements.debug.ExpandedProfileScreenDebug
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.spotme.R

@Composable
fun AddDebtTransactionScreen(
    profile: Profile?,

    ) { if(profile == null){ Text("Profile is null, please fix") } else {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Add Debt Transaction Screen", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        profile.debts.forEach { debt ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = debt.name ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(text = debt.description ?: "")
                    debt.transactions.forEach { trans ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            ),
                            modifier = Modifier
                                .wrapContentHeight()
                                .weight(1f)
                        ) {
                            Column {
                                Text(
                                    text = "$${trans.amount}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = trans.description ?: "")
                                Text(text = "Canceled - ${trans.canceled}")
                                Text(text = "Transaction Made - ${trans.date}\n")
                            }
                        }
                    }
                }
            }
        }
    }
}


}

