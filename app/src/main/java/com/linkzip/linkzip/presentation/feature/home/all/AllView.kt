package com.linkzip.linkzip.presentation.feature.home.all

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.presentation.component.LinkGroupComponent
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.HomeNavigation

@Composable
fun AllView (
    homeViewModel: HomeViewModel =  hiltViewModel()
){
    val groupEvent by homeViewModel.allGroupListFlow.collectAsStateWithLifecycle(null)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (groupEvent != null){
            GroupList(groupEvent!!)
        }

        Text(
            text = stringResource(R.string.add_group),
            style = LinkZipTheme.typography.grayNormal16
        )
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
                    //아이콘이랑 컬러를 이렇게 넣는게 맞는걸까?
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
        AllView()
    }
}