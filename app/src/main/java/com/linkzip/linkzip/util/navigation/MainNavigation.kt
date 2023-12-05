package com.linkzip.linkzip.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.ScreenState
import com.linkzip.linkzip.presentation.feature.home.HomeView
import com.linkzip.linkzip.presentation.feature.main.MainView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.my.MyPageView

sealed class MainPath(val path: String) {
    object Home : MainPath("Home")
    object Mypage : MainPath("Mypage")
}

@Composable
fun MainNavigation(
    mainViewModel: MainViewModel = viewModel()
){
    val navController = rememberNavController()
    val screenState by mainViewModel.screenState.collectAsState(initial = ScreenState.HOME)

    LaunchedEffect(screenState) {
        when(screenState) {
            ScreenState.HOME -> { navController.navigate(MainPath.Home.path)}
            ScreenState.MYPAGE -> { navController.navigate(MainPath.Mypage.path) }
        }
    }

    NavHost(
        navController =  navController,
        startDestination = MainPath.Home.path){
        composable(MainPath.Home.path) {
            MainView()
        }
        composable(MainPath.Mypage.path) {
            MyPageView()
        }
    }
}