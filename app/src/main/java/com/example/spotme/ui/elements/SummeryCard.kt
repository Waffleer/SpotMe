package com.example.spotme.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotme.data.Person
import com.example.spotme.data.StaticDataSource

@Composable
fun SummaryCard(
    person: Person,
    modifier: Modifier = Modifier
    ) {
    var total: Double = 0.0

    person.debts.forEach{debt -> //Totals all of the transactions for every debt
        debt.transactions.forEach{ trans ->
            if(trans.canceled == false){
                total += trans.amount
            }
        }
    }



    Card(modifier = modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(BorderStroke(2.dp, SolidColor(Color.Black))),
    ) {
        Column {
            Text(
                textAlign = TextAlign.Start,
                text = person.displayName,
                modifier = Modifier
                    .padding(start = 12.dp, top = 4.dp),
            )
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
            )
            Text(
                textAlign = TextAlign.Start,
                text = "$$total",
                modifier = Modifier
                    .padding(start = 12.dp, bottom = 4.dp),
            )
        }
    }
}


@Preview
@Composable
fun PreviewSummeryCard(){
    SummaryCard(person = StaticDataSource.persons[0])
}