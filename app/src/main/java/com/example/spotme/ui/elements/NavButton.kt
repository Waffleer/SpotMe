package com.example.spotme.ui.elements

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
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
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(11.dp),

    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
        )
    }
}

@Composable
fun ToDetailsNavButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(11.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.List,
            contentDescription = stringResource(R.string.list_button),
            modifier = modifier
                .size(dimensionResource(R.dimen.navbar_icon_size))
            )
    }
}

@Composable
fun AddProfileNavButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(11.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = stringResource(R.string.add_button),
            modifier = modifier
                .size(dimensionResource(R.dimen.navbar_icon_size))
            )
    }
}

@Composable
fun TestingNavButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(11.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Build,
            contentDescription = stringResource(R.string.testing_button),
            modifier = modifier
                .size(dimensionResource(R.dimen.navbar_icon_size))
            )
    }
}

@Composable
fun ToSummaryNavButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(11.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = stringResource(R.string.home_button),
            modifier = modifier
                .size(dimensionResource(R.dimen.navbar_icon_size))
        )
    }
}

@Composable
fun ToEditProfileNavButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            //.padding(11.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(R.string.edit_button),
            modifier = modifier
                .size(dimensionResource(R.dimen.small_icon_size))
        )
    }
}

//POTENTIALLY WILL BE USED IN THE FUTURE NOT SURE THO, TOO LAZY FOR THIS
//@Composable
//fun SpotMeNavigationBar(
//    onDetailsPressed: () -> Unit,
//    onTestPressed: () -> Unit,
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        ToDetailsNavButton(
//            labelResourceId = R.string.details,
//            onClick = { onDetailsPressed() },
//            modifier = Modifier.padding(4.dp)
//        )
//        AddProfileNavButton(
//            labelResourceId = R.string.add_profile,
//            onClick = {},
//            modifier = Modifier.padding(4.dp)
//        )
//        TestingNavButton(
//            labelResourceId = R.string.TestingScreen,
//            onClick = { onTestPressed() },
//            modifier = Modifier.padding(4.dp)
//        )
//    }
//}
