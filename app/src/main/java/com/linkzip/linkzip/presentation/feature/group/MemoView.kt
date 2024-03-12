package com.linkzip.linkzip.presentation.feature.group

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.component.CommonButton
import com.linkzip.linkzip.presentation.component.HeaderTitleView
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun MemoView(data: Triple<GroupData?, IconData?, LinkData?>?, onBackButtonPressed: () -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    fun hideKeyBoard() {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    Column(modifier = Modifier
        .padding(bottom = 19.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                hideKeyBoard()
            })
        }) {
        HeaderTitleView(
            backgroundColor = LinkZipTheme.color.white,
            onBackButtonPressed = onBackButtonPressed,
            onActionButtonPressed = null,
            title = "메모"
        )
        EditLinkMemo(data = data, modifier = Modifier.weight(1f))

    }
}

@Composable
fun EditLinkMemo(data: Triple<GroupData?, IconData?, LinkData?>?, modifier: Modifier) {
    var isFocused by remember { mutableStateOf(false) }
    var memoText by remember { mutableStateOf(TextFieldValue(data?.third?.linkMemo ?: "")) }
    val maxMemoLength = 100

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(162.dp)
            .padding(horizontal = 22.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        value = memoText,
        onValueChange = {
            if (it.text.count() <= maxMemoLength) memoText = it
        },
        textStyle = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg70),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                if (memoText.text.isNotEmpty()) {
                    Text(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        text = "${memoText.text.length}/$maxMemoLength",
                        style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg40),
                    )
                }
                // TextField hint
                if (memoText.text.isBlank()) {
                    Text(
                        text = "링크를 통해 발견한 인사이트가 있나요?",
                        style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg40)
                    )
                }

                // TextField
                innerTextField()
            }

        }
    )

    Spacer(modifier = modifier)
    CommonButton(
        buttonName = "저장하기",
        enable = memoText.text.isNotEmpty(),
        buttonColor = Color(data!!.second!!.iconButtonColor),
        isFocused = isFocused,
        onClickEvent = {

        }
    )
}
