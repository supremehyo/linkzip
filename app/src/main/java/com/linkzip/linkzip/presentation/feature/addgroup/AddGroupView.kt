package com.linkzip.linkzip.presentation.feature.addgroup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.R
import com.linkzip.linkzip.presentation.HeaderTitleView
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.NO_GROUP
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.PLUS
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun AddGroupView(onBackButtonPressed: () -> Unit) {
    var groupNameText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
    ) {
        HeaderTitleView(onBackButtonPressed, "그룹 추가")
        Spacer(modifier = Modifier.height(28.dp))
        iconView(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(41.dp))
        Text(
            text = "그룹명",
            color = LinkZipTheme.color.wg50,
            style = LinkZipTheme.typography.medium14
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = groupNameText,
            onValueChange = { groupNameText = it },
            textStyle = LinkZipTheme.typography.medium16,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = LinkZipTheme.color.wg10,
                            shape = RoundedCornerShape(size = 12.dp)
                        ).padding(vertical = 16.dp)
                ) {
                    innerTextField()
                }
            },
        )
    }
}

@Composable
fun iconView(modifier: Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.icon_nogroup),
            contentDescription = NO_GROUP,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        )
        IconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {

            }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_circle_plus_white),
                contentDescription = PLUS
            )
        }
    }
}

object AddGroupView {
    const val NO_GROUP = "NO_GROUP"
    const val PLUS = "PLUS"
}