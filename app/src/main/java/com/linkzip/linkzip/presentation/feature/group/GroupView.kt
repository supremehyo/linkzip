package com.linkzip.linkzip.presentation.feature.group

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.BottomDialogMenu
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.BaseViewModel
import com.linkzip.linkzip.presentation.component.BottomDialogComponent
import com.linkzip.linkzip.presentation.component.BottomDialogLinkAddGroupMenuComponent
import com.linkzip.linkzip.presentation.component.BottomDialogMenuComponent
import com.linkzip.linkzip.presentation.component.CustomToast
import com.linkzip.linkzip.presentation.component.DialogComponent
import com.linkzip.linkzip.presentation.component.HeaderTitleView
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.ToastType
import com.linkzip.linkzip.util.composableActivityViewModel


@Composable
fun GroupView(
    groupData: Triple<GroupData?, IconData?, LinkData?>?,
    onBackButtonPressed: () -> Unit,
    onActionButtonPressed: () -> Unit,
    onActionLinkEditPressed: (LinkData) -> Unit,
    onClickMemoPressed: (LinkData) -> Unit,
    onActionLinkPressed : (LinkData) -> Unit,
    groupViewModel: GroupViewModel = hiltViewModel(),
    baseViewModel: BaseViewModel = composableActivityViewModel()
) {
    val backgroundColor = remember { groupData?.second?.iconHeaderColor }
    val groupName = remember { groupData?.first?.groupName }

    // 링크 선택 ON/OFF 여부
    var isStatusSelectLink by remember { mutableStateOf(false) }

    // 링크 삭제 다이얼로그 visible 여부
    var isDeleteSelectLink by remember { mutableStateOf(false) }

    // 링크 삭제 success/fail 에 따라 토스트 표기
    var isShowToastDeleteLink by remember { mutableStateOf(Pair(ToastType.SUCCESS, false)) }

    // 그룹 이동 선택 시 보여지는 아이콘 + 그룹 리스트
    val iconList by baseViewModel.iconListByGroup.collectAsStateWithLifecycle()
    val groupList by baseViewModel.allGroupListFlow.collectAsStateWithLifecycle()

    val linkList by groupViewModel.linkListByGroup.collectAsStateWithLifecycle()
    val favoriteList by groupViewModel.favoriteList.collectAsStateWithLifecycle(emptyList())
    val unFavoriteList by groupViewModel.unFavoriteList.collectAsStateWithLifecycle(emptyList())

    // 선택한 링크들
    val selectLinkList by groupViewModel.selectLinkList.collectAsStateWithLifecycle()

    var showBottomDialog by remember { mutableStateOf(false) }

    LaunchedEffect(linkList, selectLinkList) {
        groupViewModel.getLinkListByGroup(
            groupData?.first?.groupId.toString() ?: throw NullPointerException()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        HeaderTitleView(
            backgroundColor = Color(backgroundColor ?: LinkZipTheme.color.white.toArgb()),
            onBackButtonPressed = {
                // 링크 선택이 눌려있다면, 링크선택 취소
                if (isStatusSelectLink) {
                    isStatusSelectLink = !isStatusSelectLink
                } else {
                    onBackButtonPressed.invoke()
                }
            },
            onActionButtonPressed = onActionButtonPressed,
            title = groupName ?: "error"
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextWithIcon(
                modifier = Modifier,
                iconFile = R.drawable.icon_pin,
                message = stringResource(R.string.favorite_link)
            )
            TextWithIcon(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    isStatusSelectLink = !isStatusSelectLink

                    // 선택한 or 선택된 링크 리스트 초기화
                    groupViewModel.clearSelectLinkList()
                },
                iconFile = if (isStatusSelectLink) R.drawable.ic_check_gray else R.drawable.ic_uncheck_gray,
                message = stringResource(R.string.select_link)
            )
        }
        Box(modifier = Modifier.height(8.dp))
        if (favoriteList.isNotEmpty()) {
            LazyColumn {
                items(
                    key = { data: LinkData -> data },
                    items = favoriteList
                ) { data ->
                    Box(modifier = Modifier.clickable {
                        Log.e("adad", "click Link TODO")
                    }) {
                        LinkInGroup(
                            data,
                            onClickMemoPressed,
                            onActionLinkEditPressed,
                            onActionLinkPressed,
                            isStatusSelectLink
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .height(4.dp)
                .fillMaxWidth()
                .background(LinkZipTheme.color.wg10)
        )

        if (unFavoriteList.isNotEmpty()) {
            LazyColumn {
                items(
                    key = { data: LinkData -> data },
                    items = unFavoriteList
                ) { data ->
                    Box(modifier = Modifier.clickable {
                        Log.e("adad", "click Link TODO")
                    }) {
                        LinkInGroup(
                            data,
                            onClickMemoPressed,
                            onActionLinkEditPressed,
                            onActionLinkPressed,
                            isStatusSelectLink
                        )
                    }
                }
            }
        }

        if (isStatusSelectLink) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 11.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = LinkZipTheme.color.wg20,
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .padding(horizontal = 44.dp, vertical = 18.dp)
                        .clickable {
                            if (isStatusSelectLink) {
                                showBottomDialog = true
                            }
                        }
                ) {
                    Text(
                        text = "그룹 이동",
                        style = LinkZipTheme.typography.medium16.copy(color = LinkZipTheme.color.wg70)
                    )
                }
                Box(modifier = Modifier
                    .background(
                        color = LinkZipTheme.color.red,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(horizontal = 44.dp, vertical = 18.dp)
                    .clickable {
                        isDeleteSelectLink = true
                    }
                ) {
                    Text(
                        text = "선택 삭제",
                        style = LinkZipTheme.typography.medium16.copy(color = LinkZipTheme.color.white)
                    )
                }
            }
        }
    }

    if(isDeleteSelectLink && selectLinkList.isEmpty()) {
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(message = "링크를 선택해주세요", icon = R.drawable.ic_check)
        isDeleteSelectLink = false
    }

    // 링크 선택 -> 링크 삭제 눌렀을 때 뜨는 확인 다이얼로그제
    DialogComponent(
        onDismissRequest = { isDeleteSelectLink = false },
        visible = isDeleteSelectLink,
        cancelButtonText = "취소",
        successButtonText = "삭제",
        content = "선택한 링크를 삭제할까요?",
        onClickEvent = {
            isDeleteSelectLink = false
            groupViewModel.deleteSelectListInDB(
                groupId = groupData?.first?.groupId.toString(),
                success = {
                    isShowToastDeleteLink = Pair(ToastType.SUCCESS, true)
                },
                fail = {
                    isShowToastDeleteLink = Pair(ToastType.FAIL, true)
                }
            )
        }
    )

    // 링크 선택 -> 그룹 이동 눌렀을 때 뜨는 바텀 시트
    MoveLinkGroupBottomSheet(showBottomDialog, selectLinkList, groupList, iconList,
        completeCallBack = { uid, updateGroupId ->
            showBottomDialog = false
            groupViewModel.updateGroupId(
                newGroupId = updateGroupId,
                uid = uid,
                success = {
                    groupViewModel.clearSelectLinkList()
                    isStatusSelectLink = false
                },
                fail = {
                    isStatusSelectLink = false
                }
            )
        },
        cancelCallBack = {
            showBottomDialog = false
        }
    )

    // 링크 삭제 완료되었을 때 뜨는 토스트 메시지
    if (isShowToastDeleteLink.second) {
        val msg = if (isShowToastDeleteLink.first == ToastType.SUCCESS) {
            "링크 삭제완료!"
        } else {
            "잠시 후 다시 시도해주세요"
        }

        isStatusSelectLink = false
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(message = msg, icon = R.drawable.ic_check)
        isShowToastDeleteLink = Pair(ToastType.SUCCESS, false)
    }
}

// 링크 선택 -> 그룹 이동 선택했을 때 뜨는 바텀 시트
@Composable
fun MoveLinkGroupBottomSheet(
    isShow: Boolean,
    link: List<LinkData>,
    groupList: List<GroupData>,
    iconList: List<IconData>,
    cancelCallBack: () -> Unit,
    completeCallBack: (Long, String) -> Unit
) {
    // 선택한 링크가 없을 경우
    if(link.isEmpty() && isShow) {
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(message = "링크를 선택해주세요", icon = R.drawable.ic_check)
        cancelCallBack.invoke()
    } else {

        groupList.let {
            BottomDialogComponent(
                onDismissRequest = { cancelCallBack() },
                visible = isShow,
                horizontalMargin = 20.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "이동할 그룹 선택",
                        style = LinkZipTheme.typography.medium18,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 32.dp)
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(29.dp),
                        modifier = Modifier.heightIn(min = 0.dp, max = 600.dp)
                    ) {
                        itemsIndexed(it) { index, data ->
                            BottomDialogLinkAddGroupMenuComponent(
                                groupData = data,
                                iconData = iconList[index]
                            ) {
                                link.forEach {
                                    completeCallBack.invoke(
                                        it.uid ?: throw NullPointerException(),
                                        data.groupId.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextWithIcon(modifier: Modifier, iconFile: Int, message: String) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .width(16.dp)
                .height(16.dp),
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

@Composable
fun LinkInGroup(
    link: LinkData,
    onClickMemoPressed: (LinkData) -> Unit,
    onActionLinkEditPressed: (LinkData) -> Unit,
    onActionLinkPressed: (LinkData)->Unit,
    isStatusSelectLink: Boolean,
    groupViewModel: GroupViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isSelected by remember { mutableStateOf(false) }
    var isShowToastFavorite by remember { mutableStateOf(Pair(ToastType.SUCCESS, false)) }

    val favoriteMenuItems =
        mutableListOf(
            BottomDialogMenu.ShareLink,
            BottomDialogMenu.ModifyLink,
            BottomDialogMenu.FavoriteLink,
            BottomDialogMenu.None
        )

    val unFavoriteMenuItems =
        mutableListOf(
            BottomDialogMenu.ShareLink,
            BottomDialogMenu.ModifyLink,
            BottomDialogMenu.UnFavoriteLink,
            BottomDialogMenu.None
        )

    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.clickable {
                onActionLinkPressed(link)
            }
        ){
            Row(
                Modifier.width(320.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(128.dp)
                        .height(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillBounds,
                    model = link.linkThumbnail,
                    contentDescription = null,
                )
                Box(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = link.linkTitle,
                        style = LinkZipTheme.typography.bold14.copy(color = LinkZipTheme.color.wg70)
                    )
                    Box(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.clickable {
                            onClickMemoPressed.invoke(link)
                        },
                        text = if (link.linkMemo.isNotEmpty()) "메모 확인하기" else "메모 추가하기",
                        style = LinkZipTheme.typography.medium12.copy(color = LinkZipTheme.color.wg50),
                        textDecoration = TextDecoration.Underline,
                    )
                }
            }
        }
        if (isStatusSelectLink) {
            IconButton(onClick = {
                isSelected = !isSelected
                if (isSelected) {
                    groupViewModel.addDataInSelectList(link)
                } else {
                    groupViewModel.deleteDataInSelectList(link)
                }
            }) {
                Icon(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = if (isSelected) R.drawable.ic_check_gray else R.drawable.ic_uncheck_gray),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        } else {
            IconButton(onClick = {
                showDialog = !showDialog
            }) {
                Icon(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.icon_threedots),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        }


        val menuItems = if (link.favorite) unFavoriteMenuItems else favoriteMenuItems
        BottomDialogComponent(
            onDismissRequest = { showDialog = false },
            visible = showDialog,
            horizontalMargin = 29.5.dp
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(29.dp)
            ) {
                items(menuItems.size - 1) { it ->
                    BottomDialogMenuComponent(
                        menuItems = menuItems[it]
                    ) {
                        showDialog = false
                        when (it) {
                            BottomDialogMenu.ShareLink -> {
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, link.link)
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(shareIntent, null))
                            }

                            BottomDialogMenu.ModifyLink -> {
                                onActionLinkEditPressed(link)
                            }

                            BottomDialogMenu.FavoriteLink, BottomDialogMenu.UnFavoriteLink -> {
                                groupViewModel.updateFavoriteLink(
                                    link,
                                    success = {
                                        isShowToastFavorite = Pair(ToastType.SUCCESS, true)
                                    },
                                    fail = {
                                        isShowToastFavorite = Pair(ToastType.FAIL, true)
                                    })
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    if (isShowToastFavorite.second) {
        val msg = if (isShowToastFavorite.first == ToastType.SUCCESS) {
            if (link.favorite) "즐겨찾기 설정완료!" else "즐겨찾기 해제완료!"
        } else {
            "잠시 후 다시 시도해주세요"
        }

        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(message = msg, icon = R.drawable.ic_check)
        isShowToastFavorite = Pair(ToastType.SUCCESS, false)
    }
}