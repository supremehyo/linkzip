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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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
            .imePadding()
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
            style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        editGroupName()
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(vertical = 16.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = LinkZipTheme.color.wg70
            ),
        ){
            Text(
                text = "저장하기",
                style = LinkZipTheme.typography.medium16.copy(color = LinkZipTheme.color.white)
            )
        }
    }
}

// 그룹명 작성 TextField
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
                // TextField hint
                if (groupNameText.text.isEmpty()) {
                    Text(
                        text = "면접질문, 운동, 브이로그 등",
                        style = LinkZipTheme.typography.medium16.copy(color = LinkZipTheme.color.wg40)
                    )
                }

                // TextField
                innerTextField()

                // TextField count
                if (groupNameText.text.isNotEmpty()) {
                    Text(
                        text = "${groupNameText.text.count()}/$maxLength",
                        style = LinkZipTheme.typography.medium12.copy(color = LinkZipTheme.color.wg40)
                    )
                }
            }
        },
    )
}

// icon View
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
        plusIconAndBottomSheet(Modifier.align(Alignment.BottomEnd))
    }
}

// plus 클릭 시, 아이콘 추가 BottomSheet 생성
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun plusIconAndBottomSheet(modifier: Modifier) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    IconButton(
        modifier = modifier,
        onClick = {
            showBottomSheet = true
        }) {
        Icon(
            painter = painterResource(id = R.drawable.icon_circle_plus_white),
            contentDescription = PLUS,
            tint = Color.Unspecified
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
                    .width(336.dp),
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // BottomSheet View
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 18.dp),
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(16) { item ->
                        Icon(
                            painter = painterResource(id = R.drawable.icon_nogroup),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

object AddGroupView {
    const val NO_GROUP = "NO_GROUP"
    const val PLUS = "PLUS"
}