package com.linkzip.linkzip.presentation.feature.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.BottomDialogMenu
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.component.BottomDialogComponent
import com.linkzip.linkzip.presentation.component.BottomDialogMenuComponent
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.MainBottomNavigation
import com.linkzip.linkzip.util.navigation.MainBottomNavigationGraph
import com.linkzip.linkzip.util.navigation.MainBottomPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainView(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    var showDialog by remember { mutableStateOf(false) }
    val menuState by mainViewModel.menuState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val items = listOf(
        MainBottomPath.Home,
        MainBottomPath.MyPage
    )
    val menuItems = listOf(
        BottomDialogMenu.LinkAdd,
        BottomDialogMenu.GroupAdd,
        BottomDialogMenu.None
    )

    LaunchedEffect(true){
        mainViewModel.updateMenuState(BottomDialogMenu.None)
    }

    DisposableEffect(menuState){
        when(menuState){
            BottomDialogMenu.LinkAdd ->{
                mainViewModel.updateScreenState(MainScreenState.LINKADD.state)
            }
            BottomDialogMenu.GroupAdd ->{
                mainViewModel.updateScreenState(MainScreenState.GROUPADD.state)
            }
            else ->{
                
            }
        }

        onDispose {
            mainViewModel.updateMenuState(BottomDialogMenu.None)
        }
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FloatingActionButton(
                        modifier = Modifier
                            .size(70.dp)
                            .offset(y = 60.dp),
                        containerColor = LinkZipTheme.color.black,
                        shape = CircleShape,
                        onClick = {
                            showDialog = !showDialog
                        }
                    ) {
                        Icon(
                            tint = LinkZipTheme.color.white,
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "link_Add",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.add),
                        style = LinkZipTheme.typography.semiBold10.copy(
                            color = LinkZipTheme.color.black
                        ),
                        modifier = Modifier.offset(y = 70.dp)
                    )
                }
            }
        },
        bottomBar = { MainBottomNavigation(items, navController, mainViewModel) }
    ) {
        MainBottomNavigationGraph(navController, mainViewModel)
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier= Modifier.padding(it)
            ) {
                BottomDialogComponent(
                    onDismissRequest = { showDialog = false },
                    visible = showDialog,
                    horizontalMargin = 20.dp
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(29.dp)
                    ) {
                        items(menuItems.size-1) { it ->
                            BottomDialogMenuComponent(
                                menuItems = menuItems[it]
                            ) {
                                mainViewModel.updateMenuState(it)
                                showDialog = false
                            }
                        }
                    }
                }
            }
        }

    }
}
