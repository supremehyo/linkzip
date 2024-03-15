package com.linkzip.linkzip.presentation.feature.addlink

import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.component.BottomDialogComponent
import com.linkzip.linkzip.presentation.component.BottomDialogLinkAddGroupMenuComponent
import com.linkzip.linkzip.presentation.component.CommonButton
import com.linkzip.linkzip.presentation.component.CommonEditTextField
import com.linkzip.linkzip.presentation.component.DialogComponent
import com.linkzip.linkzip.presentation.component.FieldSize
import com.linkzip.linkzip.presentation.component.HeaderTitleView
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
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
    homeViewModel: HomeViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit
) {
    var menuItems by remember { mutableStateOf(listOf<GroupData>()) }
    var showDialog by remember { mutableStateOf(false) }
    var showBottomDialog by remember { mutableStateOf(false) }
    var groupTitle by remember { mutableStateOf("그룹을 선택해주세요.") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var isFocused by remember { mutableStateOf(false) }
    var groupIconList by remember { mutableStateOf(listOf<IconData>()) }
    var iconListFlow by remember { mutableStateOf(listOf<IconData>()) }
    var saveButtonColor by remember { mutableStateOf(Color.Black) }
    homeViewModel.getAllGroups()
    var resultData = LinkData(
        link = "",
        linkGroupId = -1L,
        linkTitle = "",
        linkMemo = "",
        createDate = "",
        updateDate = "",
        linkThumbnail = "",
        favorite = false
    )

    var resultLinkData by remember {
        mutableStateOf(
            LinkData(
                link = "",
                linkGroupId = -1L,
                linkTitle = "",
                linkMemo = "",
                createDate = "",
                updateDate = "",
                linkThumbnail = "",
                favorite = false
            )
        )
    }
    LaunchedEffect(true) {
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.iconListFlow.collect{ state ->
                iconListFlow = state
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.allGroupListFlow.collect { state ->
                menuItems = state
                homeViewModel.getIconListById(menuItems.map { it.groupIconId })
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.linkEventFlow.collect { state ->
                when (state) {
                    is HomeViewModel.LinkEvent.InsertLinkUiEvent -> {
                        when (state.uiState) {
                            is UiState.Loding -> {

                            }
                            is UiState.Success -> {
                                onBackButtonPressed.invoke()
                            }
                            else -> {
                                Log.v("resultText3", "${state.uiState.toString()}")
                            }
                        }
                    }

                    is HomeViewModel.LinkEvent.GetLinksUiEvent -> {
                        when (state.uiState) {
                            is UiState.Loding -> {

                            }

                            is UiState.Success -> {
                                Log.e("insert", "${state.uiState.data}")
                            }

                            else -> {

                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }


    DisposableEffectWithLifeCycle(
        onResume = {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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
                            if (result != null) {
                                resultLinkData = result
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
                        if (result != null) {
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
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
            }
    ) {
        HeaderTitleView(LinkZipTheme.color.white, onBackButtonPressed, null,
            stringResource(R.string.add_link_title)
        )
        Column(
            modifier = Modifier.padding(horizontal = 22.dp)
        ) {
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
                maxLength = 1000,
                fieldType = FieldSize.NORMAL,
                initialText = resultLinkData.link,
                resultText = {
                    resultLinkData.link = it.second
                    Log.v("resultText", "${it.second}")
                },
                isFocus = {
                    isFocused = it
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "그룹",
                style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
            )
            Spacer(modifier = Modifier.height(8.dp))
            dropDownMenu(
                groupTitle = groupTitle
            ) {
                showBottomDialog = true
            }
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
                    resultLinkData.linkTitle = it.second
                    Log.v("resultText", "${it.second}")
                },
                isFocus = {
                    isFocused = it
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
                    resultLinkData.linkMemo = it.second
                    Log.v("resultText", "${it.second}")
                },
                isFocus = {
                    isFocused = it
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.padding(bottom = 19.dp)
        ) {
            CommonButton(
                enable = true,
                keyBoardUpOption = true,
                buttonName = "저장하기",
                buttonColor = saveButtonColor,
                onClickEvent = {
                    CoroutineScope(Dispatchers.IO).launch {
                        if(resultLinkData.linkThumbnail.isEmpty()) {
                            resultLinkData = resultLinkData.copy(linkThumbnail = EMPTY_THUMBNAIL)
                        }

                        homeViewModel.insertLink(resultLinkData)
                    }
                },
                isFocused = isFocused
            )
        }

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
        menuItems?.let {
            BottomDialogComponent(
                onDismissRequest = { showBottomDialog = false },
                visible = showBottomDialog,
                horizontalMargin = 20.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = stringResource(R.string.selet_group),
                        style = LinkZipTheme.typography.medium18,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 32.dp)
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(29.dp)
                    ) {
                        itemsIndexed(it) { index, data ->
                            //그룹 없음
                            if (data.groupIconId != -1L) {
                                BottomDialogLinkAddGroupMenuComponent(
                                    groupData = data,
                                    iconData = iconListFlow[index]
                                ) {
                                    showBottomDialog = false
                                    groupTitle = data.groupName
                                    resultLinkData.linkGroupId = data.groupId
                                    Log.e("clickColor"," ${iconListFlow[index].iconButtonColor} ${ Color(iconListFlow[index].iconButtonColor)}")
                                    saveButtonColor = Color(iconListFlow[index].iconButtonColor)
                                }
                            }else{

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun dropDownMenu(
    groupTitle: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LinkZipTheme.color.wg10,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .border(
                width = 1.dp,
                color = LinkZipTheme.color.wg10,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(vertical = 16.dp, horizontal = 14.dp)
            .clickable {
                onClick()
            },
    ) {
        Text(
            text = "$groupTitle",
            color = LinkZipTheme.color.wg40,
            style = LinkZipTheme.typography.medium16
        )
    }
}

const val EMPTY_THUMBNAIL = "https://mblogthumb-phinf.pstatic.net/MjAyMjA1MzFfMTY4/MDAxNjUzOTI5Mjc3NzU5.4ESIx_02zDPDaboENohTwq1ejlla-rEnFjgR5Cnp6q4g.q2K3wfKEV7JpSFs0BRAAebNJNKPL7JA8xyTvAG0F0DAg.JPEG.jikim97/resized%EF%BC%BFresized%EF%BC%BFresized%EF%BC%BFIMG%EF%BC%BF0571.jpg?type=w800"