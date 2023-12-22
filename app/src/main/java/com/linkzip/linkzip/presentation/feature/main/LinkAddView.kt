package com.linkzip.linkzip.presentation.feature.main

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.presentation.HeaderTitleView
import com.linkzip.linkzip.presentation.component.CommonButton
import com.linkzip.linkzip.presentation.component.CommonEditTextField
import com.linkzip.linkzip.presentation.component.FieldSize
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LinkAddView(onBackButtonPressed: () -> Unit) {
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
        HeaderTitleView(onBackButtonPressed, "링크 추가")
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "링크(필수)",
            style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonEditTextField(
            title = "링크",
            textCountOption = false,
            hintText = "복사한 URL을 붙여넣어주세요.",
            fieldType = FieldSize.NORMAL,
            resultText = {
                Log.v("resultText" , "${it.second}")
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "그룹",
            style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonEditTextField(
            title = "그룹",
            textCountOption = false,
            hintText = "그룹을 선택해주세요",
            fieldType = FieldSize.NORMAL,
            resultText = {
                Log.v("resultText" , "${it.second}")
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "제목",
            style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonEditTextField(
            title = "제목",
            textCountOption = false,
            hintText = "ex.탕후루 만들기",
            fieldType = FieldSize.NORMAL,
            resultText = {
                Log.v("resultText" , "${it.second}")
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "메모",
            style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CommonEditTextField(
            title = "메모",
            textCountOption = false,
            fieldType = FieldSize.LARGE,
            hintText = "링크를 통해 발견한 인사이트가 있나요?",
            resultText = {
                Log.v("resultText" , "${it.second}")
            }
        )

        CommonButton(
            enable = false,
            keyBoardUpOption = true,
            buttonName = "저장하기",
            onClickEvent = {

            }
        )
    }
}
