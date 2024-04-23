package com.linkzip.linkzip.util.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.presentation.feature.home.all.AllView
import com.linkzip.linkzip.presentation.feature.home.favorite.FavoriteView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.util.BackHandler
import kotlinx.coroutines.launch


sealed class HomePath(val path: String) {
    object All : HomePath("All")
    object Favorite : HomePath("Favorite")
}

@Composable
fun HomeNavigation(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    callback: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val screenState by homeViewModel.homeScreenState.collectAsState(initial = HomeScreenState.ALL)

    LaunchedEffect(screenState) {
        when (screenState) {
            HomeScreenState.ALL -> {
                navController.navigate(HomePath.All.path) {
                    popUpTo(HomePath.All.path) {
                        inclusive = true
                    }
                }
            }

            HomeScreenState.FAVORITE -> {
                navController.navigate(HomePath.Favorite.path) {
                    popUpTo(HomePath.Favorite.path) {
                        inclusive = true
                    }
                }
            }

            HomeScreenState.POPUP -> {
                navController.popBackStack()
            }
        }
    }


    NavHost(
        navController = navController,
        startDestination = HomePath.All.path
    ) {
        composable(HomePath.All.path) {
            AllView(
                onBackButtonPressed = {
                   // val context = LocalContext.current
                },
                dimmedBoolean = {
                    callback(it)
                },
                onClickAddGroup = {
                     mainViewModel.updateScreenState(MainScreenState.GROUPADD.state)
                },
                onClickGroup = { group, icon ->
                    synchronized(this) {
                        MainScreenState.GROUP.data = Triple(group, icon, null)
                        mainViewModel.updateScreenState(MainScreenState.GROUP.state)
                    }
                }
            )
        }
        composable(HomePath.Favorite.path) {
            FavoriteView(
                onActionLinkEditPressed = {groupData,iconData,linkData->
                    MainScreenState.LINKADD.data = Triple(groupData, iconData, linkData)
                    MainScreenState.LINKADD.from = "FAVORITE"
                    MainPath.LinkAdd.data = MainScreenState.LINKADD.data
                    mainViewModel.updateScreenState(MainScreenState.LINKADD.state)
                },
                onClickMemoPressed = {groupData,iconData,linkData->
                    MainScreenState.MEMO.data = Triple(groupData, iconData, linkData)
                    MainScreenState.MEMO.from = "FAVORITE"
                    mainViewModel.updateScreenState(MainScreenState.MEMO.state)
                }
            )
        }
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}