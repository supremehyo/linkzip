package com.linkzip.linkzip.presentation.feature.main

import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.service.autofill.UserData
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.HeaderTitleView
import com.linkzip.linkzip.presentation.component.CommonButton
import com.linkzip.linkzip.presentation.component.CommonEditTextField
import com.linkzip.linkzip.presentation.component.DialogComponent
import com.linkzip.linkzip.presentation.component.FieldSize
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.DisposableEffectWithLifeCycle
import com.linkzip.linkzip.util.LinkScrapData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LinkAddView(
    onBackButtonPressed: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var resultData =  LinkData(
        link = "",
        linkGroupId = "",//분류되지 않음 이라는 그룹이 자동으로 생성되어야 할듯함.
        linkTitle = "",
        linkMemo = "",
        createDate = "",
        updateDate = "",
        favorite = false
    )

    var resultLinkData by remember { mutableStateOf(
        LinkData(
            link = "",
            linkGroupId = "",//분류되지 않음 이라는 그룹이 자동으로 생성되어야 할듯함.
            linkTitle = "",
            linkMemo = "",
            createDate = "",
            updateDate = "",
            favorite = false
        )
    )}


    DisposableEffectWithLifeCycle(
        onResume = {
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (!clipboardManager.hasPrimaryClip()) {
                    // 클립보드가 비어있는 경우
                } else {
                    val clipData = clipboardManager.primaryClip
                    if (clipData != null && clipData.itemCount > 0) {
                        val text = clipData.getItemAt(0).text.toString()
                        // 클립보드에 저장된 첫 번째 텍스트 데이터 가져오기
                        CoroutineScope(Dispatchers.IO).launch {
                            var result = LinkScrapData(text)
                            Log.e("sdfsdfsdf0" , "$result")
                            if(result != null){
                                resultLinkData = result
                                Log.e("resultData" , "$resultLinkData")
                                showDialog = !showDialog
                            }
                        }
                    }
                }
            } else {
                val clipData = clipboardManager.primaryClip
                if (clipData != null && clipData.itemCount > 0) {
                    val text = clipData.getItemAt(0).text.toString()
                    // 클립보드에 저장된 첫 번째 텍스트 데이터 가져오기
                    CoroutineScope(Dispatchers.IO).launch {
                        var result = LinkScrapData(text)
                        if(result != null){
                            resultData = result
                            showDialog = !showDialog
                        }
                    }
                }
            }
        }
    )

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
            initialText = resultLinkData.link,
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
            initialText = resultLinkData.linkTitle,
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
            initialText = resultLinkData.linkMemo,
            hintText = "링크를 통해 발견한 인사이트가 있나요?",
            resultText = {
                Log.v("resultText" , "${it.second}")
            }
        )

        CommonButton(
            enable = false,
            keyBoardUpOption = true,
            buttonName = "저장하기",
            buttonColor = LinkZipTheme.color.black,
            onClickEvent = {

            }
        )

        DialogComponent(
            onDismissRequest = { showDialog = false },
            visible = showDialog,
            cancelButtonText = "취소",
            successButtonText = "붙여넣기",
            content = "복사한 링크를 붙여넣을까요?",
            onClickEvent = {
                showDialog = false
            }
        )
    }
}
