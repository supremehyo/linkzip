package com.linkzip.linkzip.presentation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.linkzip.linkzip.R
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderTitleView(onBackButtonPressed: () -> Unit, title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = LinkZipTheme.typography.wg70Medium16,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButtonPressed
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_left),
                    contentDescription = HeaderTitleView.BACK
                )
            }
        }
    )
}

object HeaderTitleView {
    const val BACK = "Back"
}