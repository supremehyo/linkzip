package com.linkzip.linkzip.presentation.feature.addgroup

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_AIRPLANE
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_BOOK
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_CAMERA
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_CAR
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_COFFEE
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_COMPUTER
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_GAME
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_GIFT
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_HEART
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_HOME
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_MEMO
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_MONEY
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_NO_GROUP
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_PALETTE
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_RICE
import com.linkzip.linkzip.data.room.IconData.Companion.ICON_WINE
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.component.BottomDialogComponent
import com.linkzip.linkzip.presentation.component.CustomToast
import com.linkzip.linkzip.presentation.component.HeaderTitleView
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.ADD
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.ADD_GROUP_TITLE
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.EDIT_GROUP_TITLE
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.PLUS
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.UPDATE
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.ToastType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddGroupView(
    groupData: Triple<GroupData?, IconData?, LinkData?>?,
    addGroupViewModel: AddGroupViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    fun hideKeyBoard() {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    val currentIconState by addGroupViewModel.currentAddGroupIcon.collectAsStateWithLifecycle()

    LaunchedEffect(groupData) {
        if (groupData != null) {
            addGroupViewModel.updateCurrentIcon(groupData.second!!)
        }
    }

    // 그룹 추가/수정 화면 관련
    val (groupName, title) = remember(groupData) {
        if (groupData == null) {
            "" to ADD_GROUP_TITLE
        } else {
            groupData.first?.groupName to EDIT_GROUP_TITLE
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    hideKeyBoard()
                })
            }
    ) {
        HeaderTitleView(LinkZipTheme.color.white,
            onBackButtonPressed = {
                onBackButtonPressed.invoke()
            }, null, title)
        Spacer(modifier = Modifier.height(28.dp))
        IconView(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 22.dp),
            currentIconState
        )
        Spacer(modifier = Modifier.height(41.dp))
        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = "그룹명",
            style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        EditGroupName(
            groupData,
            Modifier.weight(1f),
            currentIconState,
            groupName.toString(),
            onBackButtonPressed
        ) { hideKeyBoard() }
    }
}

// 그룹명 작성 TextField
@Composable
fun EditGroupName(
    groupData: Triple<GroupData?, IconData?, LinkData?>?,
    modifier: Modifier,
    currentIconState: IconData,
    groupName: String,
    onBackButtonPressed: () -> Unit,
    hideKeyBoard: () -> Unit
) {
    var groupNameText by remember { mutableStateOf(TextFieldValue(text = groupName , selection = TextRange(0))) }
    val maxLength = 12
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
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
                    if(!isFocused) {
                        Text(
                            text = "면접질문, 운동, 브이로그 등",
                            style = LinkZipTheme.typography.medium16.copy(color = LinkZipTheme.color.wg40)
                        )
                    }
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
    Spacer(modifier = modifier)
    SaveButton(
        groupData = groupData,
        isFocused = isFocused,
        currentIconState = currentIconState,
        groupNameText = groupNameText.text,
        onBackButtonPressed = onBackButtonPressed,
        hideKeyBoard = hideKeyBoard
    )
}

@Composable
fun SaveButton(
    groupData: Triple<GroupData?, IconData?, LinkData?>?,
    isFocused: Boolean,
    currentIconState: IconData,
    groupNameText: String,
    hideKeyBoard: () -> Unit,
    onBackButtonPressed: () -> Unit,
    addGroupViewModel: AddGroupViewModel = hiltViewModel()
) {
    var isShowToast by remember { mutableStateOf(Pair(ToastType.SUCCESS, false)) }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .padding(horizontal = if (isFocused) 0.dp else 22.dp)
            .height(55.dp),
        shape = if (isFocused) RoundedCornerShape(0.dp) else RoundedCornerShape(12.dp),
        onClick = {
            val isAddOrUpdate = if (groupData == null) ADD else UPDATE

            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val timeString = formatter.format(currentTime)
            if(groupNameText.isNotEmpty()){
                when (isAddOrUpdate) {
                    ADD -> {
                        addGroupViewModel.insertGroup(
                            GroupData(
                                groupIconId = currentIconState.iconId,
                                groupName = groupNameText,
                                createDate = timeString,
                                updateDate = timeString
                            ),
                            success = {
                                hideKeyBoard.invoke()
                                onBackButtonPressed.invoke()
                                isShowToast = Pair(ToastType.SUCCESS, true)
                            },
                            fail = {
                                isShowToast = Pair(ToastType.FAIL, true)
                                hideKeyBoard.invoke()
                            }
                        )
                    }

                    UPDATE -> {
                        addGroupViewModel.updateGroup(
                            uid = groupData?.first?.groupId ?: throw NullPointerException(),
                            name = groupNameText,
                            iconId = currentIconState.iconId,
                            date = timeString,
                            success = {
                                hideKeyBoard.invoke()
                                isShowToast = Pair(ToastType.SUCCESS, true)
                                onBackButtonPressed.invoke()
                            },
                            fail = {
                                isShowToast = Pair(ToastType.FAIL, true)
                                hideKeyBoard.invoke()
                            }
                        )
                    }
                }
            } else{
                isShowToast = Pair(ToastType.FAIL, true)
            }

        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(currentIconState.iconButtonColor)
        ),
    ) {
        Text(
            text = "저장하기",
            style = LinkZipTheme.typography.medium16
                .copy(color = LinkZipTheme.color.white)
        )
    }

    if(isShowToast.second) {
        val msg = if (isShowToast.first == ToastType.SUCCESS) {
            "그룹 추가완료!"
        } else {
            if(groupNameText.isEmpty()) {
                "올바른 값을 입력해주세요."
            } else {
                "잠시 후 다시 시도해주세요."
            }
        }

        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(message = msg, icon = R.drawable.ic_check)
        isShowToast = Pair(ToastType.SUCCESS, false)
    }
}


// icon View
@Composable
fun IconView(
    modifier: Modifier,
    currentIconState: IconData
) {
    Box(modifier = modifier) {
        Icon(
            painter = painterResource(
                id = getDrawableIcon(currentIconState.iconName),
            ),
            tint = Color.Unspecified,
            contentDescription = ICON_NO_GROUP,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        )
        PlusIconAndBottomSheet(Modifier.align(Alignment.BottomEnd))
    }
}

// plus 클릭 시, 아이콘 추가 BottomSheet 생성
@Composable
fun PlusIconAndBottomSheet(
    modifier: Modifier,
    addGroupViewModel: AddGroupViewModel = hiltViewModel()
) {
    val allIconList by addGroupViewModel.iconListFlow.collectAsStateWithLifecycle(null)
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        addGroupViewModel.getIconData()
    }

    IconButton(
        modifier = modifier,
        onClick = {
            showBottomSheet = true
        }) {
        Icon(
            painter = painterResource(id = R.drawable.icon_circle_plus_black),
            contentDescription = PLUS,
            tint = Color.Unspecified
        )

        BottomDialogComponent(
            onDismissRequest = { showBottomSheet = false },
            visible = showBottomSheet,
            horizontalMargin = 12.dp
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "그룹 아이콘 선택",
                style = LinkZipTheme.typography.medium18.copy(color = LinkZipTheme.color.wg70),
            )
            Log.e("adadad", "$allIconList")
            when (allIconList) {
                is UiState.Success -> {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(top = 56.dp),
                        columns = GridCells.Fixed(4),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val smartCastIconList =
                            (allIconList as UiState.Success<List<IconData>>).data
                        items(smartCastIconList.size) { item ->

                            IconButton(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp),
                                onClick = {
                                    addGroupViewModel.updateCurrentIcon(smartCastIconList[item])
                                    showBottomSheet = false
                                }) {
                                Icon(
                                    painter = painterResource(
                                        id = getDrawableIcon(
                                            smartCastIconList[item].iconName
                                        )
                                    ),
                                    contentDescription = smartCastIconList[item].iconName,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }

                is UiState.Loding -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 56.dp))
                }

                is UiState.Error -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 56.dp))
                }

                else -> {
                    Log.e("adadad", " 1212 $allIconList")
                    //      CircularProgressIndicator(modifier = Modifier.padding(top = 56.dp))
                }
            }

        }
    }
}

fun getDrawableIcon(iconName: String): Int {
    when (iconName) {
        ICON_NO_GROUP -> return R.drawable.icon_nogroup
        ICON_RICE -> return R.drawable.icon_rice
        ICON_COFFEE -> return R.drawable.icon_coffee
        ICON_WINE -> return R.drawable.icon_wine
        ICON_GAME -> return R.drawable.icon_game
        ICON_COMPUTER -> return R.drawable.icon_computer
        ICON_CAMERA -> return R.drawable.icon_camera
        ICON_MONEY -> return R.drawable.icon_money
        ICON_PALETTE -> return R.drawable.icon_palette
        ICON_GIFT -> return R.drawable.icon_gift
        ICON_MEMO -> return R.drawable.icon_memo
        ICON_BOOK -> return R.drawable.icon_book
        ICON_HOME -> return R.drawable.icon_home
        ICON_CAR -> return R.drawable.icon_car
        ICON_AIRPLANE -> return R.drawable.icon_airplane
        ICON_HEART -> return R.drawable.icon_heart
        else -> return R.drawable.icon_nogroup
    }
}

object AddGroupView {
    const val PLUS = "PLUS"
    const val ADD_GROUP_TITLE = "그룹 추가"
    const val EDIT_GROUP_TITLE = "그룹 수정"
    const val ADD = "ADD"
    const val UPDATE = "UPDATE"
}