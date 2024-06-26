package com.linkzip.linkzip.util.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.linkzip.linkzip.R
import com.linkzip.linkzip.presentation.feature.home.HomeView
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.presentation.feature.my.MyPageView
import com.linkzip.linkzip.ui.theme.LinkZipTheme

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
        contentColor = LinkZipTheme.color.blue294459
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
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                selected = currentRoute == item.path,
                alwaysShowLabel = false,
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
             MyPageView()
//            // TODO 후에 Mypage로 변경, 테스트 때문에 그룹 추가하기로 보여짐
//            AddGroupView { navController.popBackStack() }
        }
    }
}