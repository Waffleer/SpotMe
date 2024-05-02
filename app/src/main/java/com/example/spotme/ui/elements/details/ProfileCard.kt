package com.example.spotme.ui.elements.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotme.R
import com.example.spotme.data.Profile
import com.example.spotme.data.StaticDataSource
import com.example.spotme.ui.elements.DetailsNavButton

/**
 * Composable function to display a profile card.
 *
 * @param profile The profile to be displayed.
 * @param onClicked Callback function for when the profile card is clicked.
 * @param onPlusClicked Callback function for when the plus button in the profile card is clicked.
 * @param modifier Modifier for customizing the layout of the profile card.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCard(
    profile: Profile,
    onClicked: (Long) -> Unit,
    onPlusClicked: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    /**
     * Modifier function to apply total color to the profile card based on the total amount.
     *
     * @param total The total amount of the profile.
     */
    @Composable
    fun Modifier.totalColor(total: Double): Modifier {
        val color = if (total > 0) Color.Green
        else if (total == 0.0) Color.LightGray
        else Color.Red
        return this.then(Modifier.drawWithContent {
            drawContent()
            drawRect(color = color, alpha = 0.2f)
        })
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = modifier
                .padding(top = 6.dp, bottom = 6.dp)
                .clip(RoundedCornerShape(15.dp))

        ) {
            Row {
                Card(
                    onClick = { onClicked(profile.id!!) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(100.dp)
                    )
                    {
                        Text(
                            textAlign = TextAlign.Start,
                            text = profile.name,
                            modifier = Modifier
                                .padding(start = 12.dp, top = 8.dp),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold

                        )
                        Divider(
                            color = Color.Black,
                            thickness = 2.dp,
                            modifier = Modifier
                                .padding(start = 12.dp, end = 1.dp, top = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            textAlign = TextAlign.Start,
                            text = "$${profile.amount}",
                            modifier = Modifier
                                .padding(start = 12.dp, bottom = 4.dp)
                                .totalColor(profile.amount),
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailsNavButton(
                        labelResourceId = R.string.plus_button,
                        onClick = { onPlusClicked(profile.id!!) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewProfileCard() {
    ProfileCard(profile = StaticDataSource.profiles[0], {}, {})
}