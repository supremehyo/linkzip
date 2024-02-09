package com.linkzip.linkzip.presentation.feature.home.all

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.presentation.component.IntroduceComponent
import com.linkzip.linkzip.presentation.component.LinkGroupComponent
import com.linkzip.linkzip.presentation.component.swipeLinkGroupComponent
import com.linkzip.linkzip.presentation.feature.addgroup.getDrawableIcon
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun AllView (
    homeViewModel: HomeViewModel =  hiltViewModel(),
    dimmedBoolean: (Boolean) ->Unit,
    onClickAddGroup: ()->Unit
){
    var isShowIntro by remember { mutableStateOf(true) }
    var dimmedBackground by remember { mutableStateOf(false) }
    val iconListFlow by homeViewModel.iconListFlow.collectAsStateWithLifecycle(null)
    val groupListFlow by homeViewModel.allGroupListFlow.collectAsStateWithLifecycle(null)

    var randomColors = listOf(
        LinkZipTheme.color.orangeFFE6C1,
        LinkZipTheme.color.greenBDF3C2,
        LinkZipTheme.color.pinkFFE8F7,
        LinkZipTheme.color.blueC0F0FF
    )

    LaunchedEffect(dimmedBackground){
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.setBackgroundDim(dimmedBackground)
        }
    }

    LaunchedEffect(groupListFlow){
        CoroutineScope(Dispatchers.IO).launch {
            groupListFlow?.let { list->
                homeViewModel.getIconDataById(list.map { it.groupIconId })
            }
        }
    }
    LaunchedEffect(true){
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.getAllGroups()
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // 소개 레이아웃을 지웠는지 체크하는 변수가 필요
        if(isShowIntro){
            IntroduceComponent{ isDimmed ->
                isShowIntro = !isDimmed
            }
        }


        if(groupListFlow?.isNotEmpty() == true && iconListFlow?.isNotEmpty() == true){
            groupIconComponent(groupListFlow!! , iconListFlow!!)
        }
        Box(
            modifier= Modifier
                .clickable { onClickAddGroup.invoke() }
                .fillMaxWidth(1f)
                .align(Alignment.CenterHorizontally)
                .height(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LinkZipTheme.color.wg10),
            contentAlignment = Alignment.Center
        ){
            Text(text =  AnnotatedString(stringResource(R.string.add_group)),
                style = LinkZipTheme.typography.normal16.copy(color = LinkZipTheme.color.wg50))
        }
    }
}


@Composable
fun groupIconComponent(list : List<GroupData> , iconListFlow : List<IconData>){
    var noGroup = list.find { it.groupIconId == -1L }
    Column{
        LazyColumn(){
            itemsIndexed(list.filter { it.groupIconId != -1L }){index , group ->
                swipeLinkGroupComponent {
                    LinkGroupComponent(
                        group.groupName,
                        getDrawableIcon(iconListFlow[index].iconName),
                        LinkZipTheme.color.white,
                        group.groupId
                    ){ it ->
                        Log.e("groupClick" , "$it")
                    }
                }
            }
        }
        noGroup?.let { it->
            LinkGroupComponent(
                it.groupName,
                getDrawableIcon(IconData.ICON_NO_GROUP),
                LinkZipTheme.color.white,
                it.groupId
            ){ it ->
                Log.e("groupClick" , "$it")
            }
        }
    }
}