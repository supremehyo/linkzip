package com.linkzip.linkzip.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView
import com.linkzip.linkzip.presentation.feature.main.MainView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.onboarding.OnBoardingView

sealed class MainPath(val path: String) {
    object Onboarding : MainPath("Onboarding")
    object Main : MainPath("Main")
    object GroupAdd : MainPath("GroupAdd")
}

@Composable
fun MainNavigation(
    mainViewModel: MainViewModel =  hiltViewModel()
){
    val navController = rememberNavController()
    val screenState by mainViewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(screenState) {
            when (screenState) {
                MainScreenState.ONBOARDING -> { navController.navigate(MainPath.Onboarding.path)}
                MainScreenState.MAIN -> { navController.navigate(MainPath.Main.path) }
                MainScreenState.GROUPADD -> { navController.navigate(MainPath.GroupAdd.path) }
            }
    }

    NavHost(
        navController =  navController,
        startDestination = MainPath.Onboarding.path){
        composable(MainPath.Onboarding.path) {
            OnBoardingView{
                mainViewModel.updateScreenState(MainScreenState.MAIN)
            }
        }
        composable(MainPath.Main.path) {
            MainView(mainViewModel)
        }
        composable(MainPath.GroupAdd.path) {
            AddGroupView {
                navController.popBackStack()
            }
        }
    }
}