package com.linkzip.linkzip.presentation.feature.home.all

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.presentation.BaseViewModel
import com.linkzip.linkzip.presentation.component.IntroduceComponent
import com.linkzip.linkzip.presentation.component.LinkGroupComponent
import com.linkzip.linkzip.presentation.component.SwipeScreen
import com.linkzip.linkzip.presentation.feature.addgroup.getDrawableIcon
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.composableActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AllView(
    dimmedBoolean : (Boolean)->Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    baseViewModel: BaseViewModel = composableActivityViewModel(),
    onClickAddGroup: () -> Unit,
    onClickGroup: (GroupData, IconData)-> Unit
) {
    var isShowIntro by remember { mutableStateOf(true) }
    val dimmedBackground by remember { mutableStateOf(false) }
    val iconListFlow by baseViewModel.iconListByGroup.collectAsStateWithLifecycle(null)
    val groupListFlow by baseViewModel.allGroupListFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(dimmedBackground) {
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.setBackgroundDim(dimmedBackground)
        }
    }

    LaunchedEffect(groupListFlow) {
        CoroutineScope(Dispatchers.IO).launch {
            groupListFlow?.let { list ->
                baseViewModel.getIconListById(list.map { it.groupIconId })
            }
        }
    }

    LaunchedEffect(key1 = groupListFlow){
        baseViewModel.getAllGroups()
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // 소개 레이아웃을 지웠는지 체크하는 변수가 필요
        if (isShowIntro) {
            IntroduceComponent { isDimmed ->
                isShowIntro = !isDimmed
            }
        }

        if (groupListFlow?.isNotEmpty() == true && iconListFlow?.isNotEmpty() == true) {
            GroupIconComponent(groupListFlow!!, iconListFlow!!, onClickGroup)
        }

        Box(
            modifier = Modifier
                .clickable { onClickAddGroup.invoke() }
                .fillMaxWidth(1f)
                .align(Alignment.CenterHorizontally)
                .height(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LinkZipTheme.color.wg10),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = AnnotatedString(stringResource(R.string.add_group)),
                style = LinkZipTheme.typography.normal16.copy(color = LinkZipTheme.color.wg50)
            )
        }
    }
}


@Composable
fun GroupIconComponent(
    list: List<GroupData>,
    iconListFlow: List<IconData>,
    onClickGroup: (GroupData, IconData) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    baseViewModel: BaseViewModel = composableActivityViewModel()
) {
    val noGroup = list.find { it.groupIconId == 1L }
    homeViewModel.getCountLinkInGroup(list)

    val countList by homeViewModel.countGroupLink.collectAsStateWithLifecycle(initialValue = emptyList())

    Column {
        LazyColumn(
            modifier = Modifier.heightIn(0.dp , 400.dp)
        ) {
            itemsIndexed(list.filter { it.groupIconId != 1L  }) { index, group ->
                SwipeScreen(
                    enable = group.groupName != "그룹없음",
                    buttonComposable = {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "delete",
                        )
                    },
                    contentComposable = {
                        LinkGroupComponent(
                            group.groupName,
                            getDrawableIcon(iconListFlow[index].iconName),
                            LinkZipTheme.color.white,
                            group.groupId,
                            countList.find { it.first == group.groupId }?.second ?: 0
                        ) { it ->
                            onClickGroup.invoke(group, iconListFlow[index])
                        }
                    },
                    buttonModifier = Modifier,
                    clickAction = {
                        homeViewModel.deleteGroupAndUpdateLinks(group.groupId) {
                            baseViewModel.updateAllGroupList(it)
                        }
                    }
                )
            }
        }
        noGroup?.let { group ->
            LinkGroupComponent(
                group.groupName,
                getDrawableIcon(IconData.ICON_NO_GROUP),
                LinkZipTheme.color.white,
                group.groupId,
                countList.find { it.first == group.groupId }?.second ?: 0
            ) {
                onClickGroup.invoke(group, IconData.NO_GROUP)
            }
        }
    }
}