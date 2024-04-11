package com.example.spotme.ui.elements

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotme.R

@Composable
fun NavButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .widthIn(max = 120.dp)
            .padding(8.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Composable
fun DetailsNavButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .sizeIn(maxWidth = 200.dp)
            .padding(top = 10.dp),
        shape = CircleShape,
    ) {
        Image(
            painter = painterResource(R.drawable.plus),
            contentDescription = "Button Image",
            modifier = Modifier
                .size(10.dp)
        )
    }
}