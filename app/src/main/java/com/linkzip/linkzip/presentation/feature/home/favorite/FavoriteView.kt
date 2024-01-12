package com.linkzip.linkzip.presentation.feature.home.favorite

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.component.LinkGroupComponent
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.HomeNavigation

@Composable
fun FavoriteView (
    homeViewModel: HomeViewModel =  hiltViewModel()
){
    val favoriteLinkList by homeViewModel.favoriteListFlow.collectAsStateWithLifecycle(null)
    homeViewModel.getFavoriteLink()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(favoriteLinkList!=null){
            FavoriteLinkList(favoriteLinkList!!)
        }else{
            //아직 즐겨찾기로 등록한 링크가 없어요
        }
    }
}

@Composable
fun FavoriteLinkList(
    list : UiState<List<LinkData>>
){
    when(list){
        is UiState.Success ->{
            LazyColumn(){
                items(list.data){ group ->
                   
                }
            }
        }
        is UiState.Loding -> {

        }
        is UiState.Error -> {
            
        }
    }
}