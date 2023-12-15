package com.linkzip.linkzip.presentation.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.MainBottomNavigation
import com.linkzip.linkzip.util.navigation.MainBottomNavigationGraph
import com.linkzip.linkzip.util.navigation.MainBottomPath

@Composable
fun MainView(){
    val navController = rememberNavController()
    val items = listOf(
        MainBottomPath.Home,
        MainBottomPath.MyPage
    )
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .size(70.dp)
                    .offset(y = 60.dp),
                containerColor = LinkZipTheme.color.black,
                shape = CircleShape,
                onClick = { /* ... */ }
            ){
                Icon(
                    tint = LinkZipTheme.color.white,
                    imageVector  = Icons.Rounded.Add ,
                    contentDescription = "link_Add",
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        bottomBar = { MainBottomNavigation(items,navController) }
    ) {
        Box(Modifier.padding(it)) {
            MainBottomNavigationGraph(navController)
        }
    }
}