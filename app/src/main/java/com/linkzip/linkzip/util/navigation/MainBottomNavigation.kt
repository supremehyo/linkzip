package com.linkzip.linkzip.util.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.feature.home.HomeView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.my.MyPageView
import com.linkzip.linkzip.presentation.feature.webview.OpenBrowser
import com.linkzip.linkzip.presentation.feature.webview.WebViewScreen
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.getActivity

sealed class MainBottomPath(
    val title: Int, val icon: Int, val path: String
) {
    object Home : MainBottomPath(R.string.bottom_home_text, R.drawable.home_icon, "HOME")
    object MyPage : MainBottomPath(R.string.bottom_my_text, R.drawable.icon_my, "MY_PAGE")
}

@Composable
fun MainBottomNavigation(
    items: List<MainBottomPath>,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        containerColor = LinkZipTheme.color.wg10,
        contentColor = LinkZipTheme.color.wg10
    ) {
        for (item in items) {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = LinkZipTheme.color.wg30,
                    selectedIconColor =  Color.Black,
                    indicatorColor = LinkZipTheme.color.wg10
                ),
                label = { Text(stringResource(id = item.title), fontSize = 9.sp ,
                    color = if(currentRoute == item.path) Color.Black else LinkZipTheme.color.wg30) },
                selected = currentRoute == item.path,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.path) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MainBottomNavigationGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = MainBottomPath.Home.path) {
        composable(MainBottomPath.Home.path) {
            HomeView(mainViewModel)
        }
        composable(MainBottomPath.MyPage.path) {
             MyPageView(
                 activity = getActivity(),
                 appVersion = mainViewModel.versionCode,
                 onMove = {
                     navController.navigate("${MainPath.MyWebView.path}/${it}")
                 }
             )
        }
        composable(
            route = "${MainPath.MyWebView.path}/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        ){ backstackEntry ->
            val data = backstackEntry.arguments?.getString("data")
            data?.let {
                OpenBrowser(
                    url = convertLink(it),
                    onBackButtonPressed = {
                        navController.navigate(MainBottomPath.MyPage.path)
                    }
                )
            }
        }
    }
}

fun convertLink(data : String) : String{
    return when(data){
        "GUIDE" -> "https://shimmer-salesman-a21.notion.site/49d033f2f81b408fb3e2e5414124421e"
        "USE" -> "https://shimmer-salesman-a21.notion.site/cc696e394e234bc59d947e9fbf9744c8?pvs=4"
        else -> ""
    }
}