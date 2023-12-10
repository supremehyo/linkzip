package com.linkzip.linkzip.util.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.data.model.ScreenState
import com.linkzip.linkzip.presentation.feature.home.HomeView
import com.linkzip.linkzip.presentation.feature.main.MainView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.my.MyPageView
import com.linkzip.linkzip.presentation.feature.onboarding.OnBoardingView

sealed class MainPath(val path: String) {
    object Onboarding : MainPath("Onboarding")
    object Main : MainPath("Main")
    object GroupAdd : MainPath("GroupAdd")
}

@Composable
fun MainNavigation(
    mainViewModel: MainViewModel = viewModel()
){
    val navController = rememberNavController()
    val screenState by mainViewModel.screenState.collectAsStateWithLifecycle()


    LaunchedEffect(screenState) {
        when(screenState) {
            MainScreenState.ONBOARDING -> { navController.navigate(MainPath.Onboarding.path)}
            MainScreenState.MAIN -> { navController.navigate(MainPath.Main.path) }
            MainScreenState.GROUPADD -> { navController.navigate(MainPath.GroupAdd.path) }
        }
    }

    NavHost(
        navController =  navController,
        startDestination = MainPath.Onboarding.path){
        composable(MainPath.Onboarding.path) {
            OnBoardingView()
        }
        composable(MainPath.Main.path) {
            MainView()
        }
        composable(MainPath.GroupAdd.path) {
            MyPageView()
        }
    }
}