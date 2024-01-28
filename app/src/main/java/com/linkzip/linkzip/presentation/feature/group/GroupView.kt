package com.linkzip.linkzip.presentation.feature.group

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.linkzip.linkzip.presentation.component.HeaderTitleView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GroupView(onBackButtonPressed: () -> Unit) {
    HeaderTitleView(onBackButtonPressed = onBackButtonPressed, title = "헐")

}