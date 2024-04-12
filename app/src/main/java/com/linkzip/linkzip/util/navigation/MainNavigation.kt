package com.linkzip.linkzip.util.navigation

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.feature.addgroup.AddGroupView
import com.linkzip.linkzip.presentation.feature.addlink.LinkAddView
import com.linkzip.linkzip.presentation.feature.group.GroupView
import com.linkzip.linkzip.presentation.feature.group.MemoView
import com.linkzip.linkzip.presentation.feature.main.MainView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.onboarding.OnBoardingView
import com.linkzip.linkzip.presentation.feature.webview.WebViewScreen
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.extention.navigateSingleTopTo

sealed class MainPath(val path: String, var data: Triple<GroupData?, IconData?, LinkData?>?) {
    object None : MainPath("None", null)
    object Onboarding : MainPath("Onboarding", null)
    object Main : MainPath("Main", null)
    object GroupAdd : MainPath("GroupAdd", null)
    object LinkAdd : MainPath("LinkAdd", null)
    object Group : MainPath("Group", null)
    object Memo : MainPath("Memo", null)
    object WebView : MainPath("WebView", null)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val screenState by mainViewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(screenState) {
        when (screenState) {
            MainScreenState.NONE.state -> {
                navController.navigateSingleTopTo(MainPath.None.path)
            }

            MainScreenState.ONBOARDING.state -> {
                navController.navigateSingleTopTo(MainPath.Onboarding.path)
            }

            MainScreenState.MAIN.state -> {
                navController.navigateSingleTopTo(MainPath.Main.path)
            }

            MainScreenState.GROUPADD.state -> {
                navController.navigateSingleTopTo(MainPath.GroupAdd.path)
            }

            MainScreenState.LINKADD.state -> {
                MainPath.LinkAdd.data = MainScreenState.LINKADD.data
                navController.navigateSingleTopTo(MainPath.LinkAdd.path)
            }

            MainScreenState.GROUP.state -> {
                MainPath.Group.data = MainScreenState.GROUP.data
                navController.navigateSingleTopTo(MainPath.Group.path)
            }

            MainScreenState.MEMO.state -> {
                MainPath.Memo.data = MainScreenState.GROUP.data
                navController.navigateSingleTopTo(MainPath.Memo.path)
            }
            MainScreenState.WEBVIEW.state->{
                navController.navigateSingleTopTo(MainPath.WebView.path)
            }
        }
    }



    NavHost(
        navController = navController,
        startDestination = MainPath.None.path
    ) {
        composable(MainPath.None.path) {

        }

        composable(MainPath.Onboarding.path) {
            OnBoardingView {
                mainViewModel.updateScreenState(MainScreenState.MAIN.state)
            }
        }
        composable(MainPath.Main.path) {
            val view = LocalView.current
            val window = (view.context as Activity).window
            window.statusBarColor = LinkZipTheme.color.white.toArgb()
            // 그룹 데이터 초기화
            //여기서 하니까 즐겨찾기 링크 탭에서 수정하기로 갈때마다 초기화 해버려서 문제가 있었음
            //MainPath.GroupAdd.data = null
            //MainPath.LinkAdd.data = null
            MainView(mainViewModel)
        }
        composable(MainPath.GroupAdd.path) {
            AddGroupView(MainPath.GroupAdd.data) {
                mainViewModel.updateScreenState(MainScreenState.MAIN.state)
            }
        }
        composable(MainPath.LinkAdd.path) {
            LinkAddView(
                MainPath.LinkAdd.data
            ) { it->
                if(MainScreenState.LINKADD.from == "FAVORITE"){
                    mainViewModel.updateScreenState(MainScreenState.MAIN.state)
                    MainScreenState.LINKADD.from = ""
                    MainScreenState.LINKADD.data = null
                }else{
                    if(it == "MAIN"){
                        mainViewModel.updateScreenState(MainScreenState.MAIN.state)
                    }else{
                        mainViewModel.updateScreenState(MainScreenState.GROUP.state)
                    }
                    MainScreenState.LINKADD.data = null
                }
            }
        }
        composable(MainPath.Group.path) {
            GroupView(
                groupData = MainPath.Group.data,
                onBackButtonPressed = {
                    mainViewModel.updateScreenState(MainScreenState.MAIN.state)
                },
                onActionButtonPressed = {
                    MainPath.GroupAdd.data = MainScreenState.GROUP.data
                    mainViewModel.updateScreenState(MainScreenState.GROUPADD.state)
                },
                onClickMemoPressed = {
                    MainScreenState.MEMO.data = Triple(MainPath.Group.data?.first, MainPath.Group.data?.second, it)
                    mainViewModel.updateScreenState(MainScreenState.MEMO.state)
                },
                onActionLinkPressed = { link ->
                    MainScreenState.WEBVIEW.linkData = link
                    mainViewModel.updateScreenState(MainScreenState.WEBVIEW.state)
                },
                onActionLinkEditPressed = {
                    MainScreenState.LINKADD.data = Triple(MainPath.LinkAdd.data?.first, MainPath.LinkAdd.data?.second, it)
                    mainViewModel.updateScreenState(MainScreenState.LINKADD.state)
                }
            )
        }
        composable(MainPath.Memo.path) {
            MemoView(
                data =  MainScreenState.MEMO.data,
                onBackButtonPressed = {
                    if(MainScreenState.MEMO.from == "FAVORITE"){
                        mainViewModel.updateScreenState(MainScreenState.MAIN.state)
                    }else{
                        mainViewModel.updateScreenState(MainScreenState.GROUP.state)
                    }
                })
        }
        composable(MainPath.WebView.path){
            WebViewScreen(
                linkUrl = MainScreenState.WEBVIEW.linkData?.link,
                onBackButtonPressed = {
                    mainViewModel.updateScreenState(MainScreenState.GROUP.state)
                }
            )
        }
    }
}