package com.linkzip.linkzip.presentation.feature.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.presentation.component.HeaderTitleView
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GroupView(groupData: GroupData?, onBackButtonPressed: () -> Unit, onActionButtonPressed: () -> Unit) {
    val backgroundColor = LinkZipTheme.color.orangeFFE6C1
    val groupName = groupData?.groupName

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        HeaderTitleView(
            backgroundColor = backgroundColor,
            onBackButtonPressed = onBackButtonPressed,
            onActionButtonPressed = onActionButtonPressed,
            title = groupName ?: "error"
        )
        Spacer(modifier = Modifier.height(32.dp))
        textWithIcon(
            modifier = Modifier.padding(start = 22.dp),
            iconFile = R.drawable.icon_pin,
            message = stringResource(R.string.favorite_link)
        )

    }
}


@Composable
fun textWithIcon(modifier: Modifier, iconFile: Int, message: String) {
    Row(modifier = modifier) {
        Icon(
            painter = painterResource(id = iconFile),
            contentDescription = "favorite",
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = message,
            style = LinkZipTheme.typography.medium12.copy(color = LinkZipTheme.color.wg70)
        )
    }
}