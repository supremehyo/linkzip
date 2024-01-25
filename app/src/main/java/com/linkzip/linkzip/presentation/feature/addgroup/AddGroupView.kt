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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
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
import com.linkzip.linkzip.presentation.HeaderTitleView
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView.PLUS
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddGroupView(
    addGroupViewModel: AddGroupViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    addGroupViewModel.getIconData()
    val currentIconState = addGroupViewModel.currentAddGroupIcon.collectAsStateWithLifecycle()

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
        HeaderTitleView(onBackButtonPressed, "그룹 추가")
        Spacer(modifier = Modifier.height(28.dp))
        iconView(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 22.dp),
            currentIconState.value
        )
        Spacer(modifier = Modifier.height(41.dp))
        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = "그룹명",
            style = LinkZipTheme.typography.medium14.copy(color = LinkZipTheme.color.wg50)
        )
        Spacer(modifier = Modifier.height(8.dp))
        editGroupName(Modifier.weight(1f), currentIconState.value)

    }
}

// 그룹명 작성 TextField
@Composable
fun editGroupName(modifier: Modifier, currentIconState: IconData) {
    var groupNameText by remember { mutableStateOf(TextFieldValue("")) }
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
    Spacer(modifier = modifier)
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .padding(horizontal = if (isFocused) 0.dp else 22.dp)
            .height(55.dp),
        shape = if (isFocused) RoundedCornerShape(0.dp) else RoundedCornerShape(12.dp),
        onClick = { /*TODO*/ },
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

}

// icon View
@Composable
fun iconView(
    modifier: Modifier,
    currentIconState : IconData
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
        plusIconAndBottomSheet(Modifier.align(Alignment.BottomEnd))
    }
}

// plus 클릭 시, 아이콘 추가 BottomSheet 생성
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun plusIconAndBottomSheet(
    modifier: Modifier,
    addGroupViewModel: AddGroupViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val allIconList by addGroupViewModel.iconListFlow.collectAsStateWithLifecycle(null)

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
                modifier = Modifier.width(336.dp),
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // BottomSheet View
                when (allIconList) {
                    is UiState.Success -> {
                        LazyVerticalGrid(
                            modifier = Modifier.padding(horizontal = 18.dp),
                            columns = GridCells.Fixed(4),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            val smartCastIconList =
                                (allIconList as UiState.Success<List<IconData>>).data
                            items(smartCastIconList.size) { item ->

                                IconButton(
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
                        Log.e("add_group", "Loading")
                        CircularProgressIndicator()
                    }

                    is UiState.Error -> {
                        Log.e("add_group", "error")
                        CircularProgressIndicator()
                    }

                    else -> {
                        Log.e("add_group", "else")
                        CircularProgressIndicator()
                    }
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
}