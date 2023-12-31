package com.linkzip.linkzip.util.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView
import com.linkzip.linkzip.presentation.feature.main.LinkAddView
import com.linkzip.linkzip.presentation.feature.main.MainView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.onboarding.OnBoardingView
import com.linkzip.linkzip.util.extention.navigateSingleTopTo

sealed class MainPath(val path: String) {
    object Onboarding : MainPath("Onboarding")
    object Main : MainPath("Main")
    object GroupAdd : MainPath("GroupAdd")
    object LinkAdd : MainPath("LinkAdd")
}

@Composable
fun MainNavigation(
    mainViewModel: MainViewModel =  hiltViewModel()
){
    val navController = rememberNavController()
    val screenState by mainViewModel.screenState.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    LaunchedEffect(screenState) {
            when (screenState) {
                MainScreenState.ONBOARDING -> { navController.navigateSingleTopTo(MainPath.Onboarding.path)}
                MainScreenState.MAIN -> {
                    navController.navigateSingleTopTo(MainPath.Main.path)
                }
                MainScreenState.GROUPADD -> { navController.navigateSingleTopTo(MainPath.GroupAdd.path) }
                MainScreenState.LINKADD ->{ navController.navigateSingleTopTo(MainPath.LinkAdd.path)}
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
                mainViewModel.updateScreenState(MainScreenState.MAIN)
            }
        }
        composable(MainPath.LinkAdd.path){
            LinkAddView{
                mainViewModel.updateScreenState(MainScreenState.MAIN)
            }
        }
    }
}