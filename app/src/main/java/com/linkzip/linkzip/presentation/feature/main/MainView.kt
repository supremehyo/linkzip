package com.linkzip.linkzip.presentation.feature.main

import android.content.Context
import android.util.Log
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.HomeBottomDialogMenu
import com.linkzip.linkzip.presentation.component.BottomDialogComponent
import com.linkzip.linkzip.presentation.component.BottomDialogMenuComponent
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.MainBottomNavigation
import com.linkzip.linkzip.util.navigation.MainBottomNavigationGraph
import com.linkzip.linkzip.util.navigation.MainBottomPath

@Composable
fun MainView(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    var showDialog by remember { mutableStateOf(false) }
    val menuState by mainViewModel.menuState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val items = listOf(
        MainBottomPath.Home,
        MainBottomPath.MyPage
    )
    val menuItems = listOf(
        HomeBottomDialogMenu.LinkAdd,
        HomeBottomDialogMenu.GroupAdd
    )

    LaunchedEffect(menuState){
        when(menuState){
            HomeBottomDialogMenu.LinkAdd ->{
                Log.v("테스트" , menuState.title.toString())
                customToast(context, "링크" )
            }
            HomeBottomDialogMenu.GroupAdd ->{
                Log.v("테스트" , "그룹")
                customToast(context,"그룹")
            }else ->{
                
            }
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
                    height = 150.dp
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(29.dp)
                    ) {
                        items(menuItems.size) { it ->
                            BottomDialogMenuComponent(
                                menuImage = R.drawable.guide_image,
                                menuTitle = menuItems[it]
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

fun customToast(context : Context, message : String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}