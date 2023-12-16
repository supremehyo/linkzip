package com.linkzip.linkzip.presentation.feature.home.all

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.presentation.component.LinkGroupComponent
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun AllView (
    homeViewModel: HomeViewModel =  hiltViewModel(),
    onClickAddGroup: ()->Unit
){
    val groupEvent by homeViewModel.allGroupListFlow.collectAsStateWithLifecycle(null)
    homeViewModel.getAllGroups()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (groupEvent != null){
            GroupList(groupEvent!!)
        }

        Box(
            modifier= Modifier
                .clickable { onClickAddGroup.invoke() }
                .fillMaxWidth(1f)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 22.dp)
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
fun GroupList(
    state : UiState<List<GroupData>>
){
    when(state){
        is UiState.Success ->{
            LazyColumn(){
                items(state.data){ group ->
                    LinkGroupComponent(
                        group.groupName,
                        R.drawable.guide_image,
                        LinkZipTheme.color.orangeFFE6C1,
                        1L
                    ){ it ->
                        Log.e("groupClick" , "$it")
                    }
                }
            }
        }
        is UiState.Loding -> {

        }
        is UiState.Error -> {
            Log.e("group List Error" , state.error?.message.toString())
        }
    }
}