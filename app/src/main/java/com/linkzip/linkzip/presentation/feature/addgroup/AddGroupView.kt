package com.linkzip.linkzip.presentation.feature.addgroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.linkzip.linkzip.presentation.HeaderTitleView

@Composable
fun AddGroupView(onBackButtonPressed: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        HeaderTitleView(onBackButtonPressed, "그룹 추가")
    }
}