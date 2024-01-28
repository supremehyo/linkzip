package com.linkzip.linkzip.presentation.component

import android.app.Activity
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.linkzip.linkzip.R
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderTitleView(
    backgroundColor: Color,
    onBackButtonPressed: () -> Unit,
    onActionButtonPressed: (() -> Unit)?,
    title: String
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(backgroundColor),
        title = {
            Text(
                text = title,
                color = LinkZipTheme.color.wg70,
                style = LinkZipTheme.typography.medium16,
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
        },
        actions = {
            if(onActionButtonPressed != null) {
                IconButton(
                    onClick = onActionButtonPressed
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        contentDescription = HeaderTitleView.EDIT
                    )
                }
            }
        }
    )

    // 상태바 색상 변경
    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = backgroundColor.toArgb()
}

object HeaderTitleView {
    const val BACK = "Back"
    const val EDIT = "Edit"
}