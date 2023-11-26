package com.linkzip.linkzip.presentation.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.util.navigation.MainBottomNavigation
import com.linkzip.linkzip.util.navigation.MainBottomNavigationGraph
import com.linkzip.linkzip.util.navigation.MainBottomPath

@Composable
fun MainView(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val navController = rememberNavController()
        val items = listOf(
            MainBottomPath.Home,
            MainBottomPath.MyPage
        )


        Scaffold(
            bottomBar = {
                MainBottomNavigation(items,navController)
            }
        ) {
            Box(Modifier.padding(it)){
                MainBottomNavigationGraph(navController)
            }
        }
    }
}