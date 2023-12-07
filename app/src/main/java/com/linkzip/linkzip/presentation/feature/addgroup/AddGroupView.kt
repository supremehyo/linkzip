package com.linkzip.linkzip.presentation.feature.addgroup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.R
import com.linkzip.linkzip.presentation.HeaderTitleView
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.NO_GROUP
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.PLUS
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddGroupView(onBackButtonPressed: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
            }
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
        editGroupName()
    }
}

@Composable
fun editGroupName() {
    var groupNameText by remember { mutableStateOf(TextFieldValue("")) }
    val maxLength = 12
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        value = groupNameText,
        onValueChange = {
            if (it.text.count() <= maxLength) groupNameText = it
        },
        textStyle = LinkZipTheme.typography.medium16,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = LinkZipTheme.color.wg10,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isFocused) LinkZipTheme.color.wg40 else LinkZipTheme.color.wg10,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(vertical = 16.dp, horizontal = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (groupNameText.text.isEmpty()) {
                    Text(
                        text = "면접질문, 운동, 브이로그 등",
                        style = LinkZipTheme.typography.medium16,
                        color = LinkZipTheme.color.wg40
                    )
                }
                innerTextField()
                if (groupNameText.text.isNotEmpty()) {
                    Text(
                        text = "${groupNameText.text.count()}/$maxLength",
                        color = LinkZipTheme.color.wg40,
                        style = LinkZipTheme.typography.medium12
                    )
                }
            }
        },
    )
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