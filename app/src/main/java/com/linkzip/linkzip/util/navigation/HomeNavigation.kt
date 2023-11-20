package com.linkzip.linkzip.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.data.model.ScreenState
import com.linkzip.linkzip.presentation.feature.home.HomeView
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.presentation.feature.home.all.AllView
import com.linkzip.linkzip.presentation.feature.home.favorite.FavoriteView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.my.MyPageView


sealed class HomePath(val path: String) {
    object All : HomePath("All")
    object Favorite : HomePath("Favorite")
}

@Composable
fun HomeNavigation(
    homeViewModel: HomeViewModel =  hiltViewModel()
){
    val navController = rememberNavController()
    val screenState by homeViewModel.homeScreenState.collectAsState(initial = HomeScreenState.ALL)

    //screenState 가 바뀔때마다 취소되고 재실행
    LaunchedEffect(screenState) {
        when(screenState) {
            HomeScreenState.ALL -> {navController.navigate(HomePath.All.path) }
            HomeScreenState.FAVORITE -> { navController.navigate(HomePath.Favorite.path) }
        }
    }

    NavHost(
        navController =  navController,
        startDestination = HomePath.All.path){
        composable(HomePath.All.path) {
            AllView()
        }
        composable(HomePath.Favorite.path) {
            FavoriteView()
        }
    }
}