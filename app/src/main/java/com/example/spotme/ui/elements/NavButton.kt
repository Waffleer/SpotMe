package com.example.spotme.ui.elements

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotme.R

/**
 * Composable function to create a navigation button.
 *
 * @param labelResourceId Resource ID for the label text of the button.
 * @param onClick Callback function for button click.
 * @param modifier Modifier for customizing the button layout.
 */

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
        modifier = modifier.testTag("expanded_profile_edit_button")
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(R.string.edit_button),
            modifier = modifier
                .size(dimensionResource(R.dimen.small_icon_size))
        )
    }
}

