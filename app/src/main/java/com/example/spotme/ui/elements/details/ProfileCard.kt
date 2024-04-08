package com.example.spotme.ui.elements.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.spotme.data.Profile
import com.example.spotme.data.StaticDataSource
import androidx.compose.foundation.clickable
import com.example.spotme.ui.elements.NavButton
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCard(
    profile: Profile,
    onClicked: (Profile) -> Unit,
    onPlusClicked: (Profile) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(BorderStroke(2.dp, SolidColor(Color.Black))),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onClicked(profile) }
        ) {
            Column(Modifier.width(160.dp)) {
                Text(
                    textAlign = TextAlign.Start,
                    text = profile.name,
                    modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                )
                Divider(
                    color = Color.Black,
                    thickness = 2.dp,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
                )
                Text(
                    textAlign = TextAlign.Start,
                    text = "$${profile.amount}",
                    modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
                )
            }
            PlusButton(modifier = Modifier.padding(8.dp), onClick = { onPlusClicked(profile) })
        }
    }
}
@Composable
fun PlusButton(modifier: Modifier = Modifier, onClick: () -> Unit, iconSize: Dp = 24.dp) {
    Box(modifier = modifier) {
        Box(
            modifier = modifier.clickable(onClick = onClick)
                .size(iconSize + 24.dp)
                .background(Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_input_add),
                contentDescription = "Plus",
                tint = Color.White,
                modifier = Modifier.size(iconSize)
            )
        }

    }
}




@Preview
@Composable
fun PreviewProfileCard(){
    ProfileCard(profile = StaticDataSource.profiles[0], {}, {})
}