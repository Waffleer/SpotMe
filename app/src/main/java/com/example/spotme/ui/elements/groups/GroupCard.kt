package com.example.spotme.ui.elements.groups

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotme.R
import com.example.spotme.data.Group
import com.example.spotme.data.StaticDataSource
import com.example.spotme.ui.elements.NavButton
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(
    group: Group,
    onClicked: (Group) -> Unit,
    onPlusClicked: (Group) -> Unit,
    modifier: Modifier = Modifier
    ) {
    var total: Double = 0.0

    group.transactions.transactions.forEach{
        total += it.amount
    }

    Card(modifier = modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(BorderStroke(2.dp, SolidColor(Color.Black))),

    ) {
        Row {
            Card(
                onClick = {onClicked(group)}
            ) {
                Column(
                    Modifier.width(160.dp)
                )
                {
                    Text(
                        textAlign = TextAlign.Start,
                        text = group.name,
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
            NavButton(labelResourceId = R.string.plus_button, onClick = { onPlusClicked(group) })
        }
    }
}


@Preview
@Composable
fun PreviewGroupCard(){
    GroupCard(group = StaticDataSource.groups[0], {}, {})
}