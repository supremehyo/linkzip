package com.linkzip.linkzip.presentation.feature.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.feature.onboarding.OnBoardingView
import com.linkzip.linkzip.presentation.feature.onboarding.OnboardingPath
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.MainBottomNavigation
import com.linkzip.linkzip.util.navigation.MainBottomNavigationGraph
import com.linkzip.linkzip.util.navigation.MainBottomPath
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkZipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LinkZipTheme.color.wg10
                ) {
                        val spf = getSharedPreferences("spf", MODE_PRIVATE)
                        val isFirst = viewModel.checkFirstStart(spf)
                        var screenState = viewModel.screenState.collectAsStateWithLifecycle()

                        if(!isFirst){
                            viewModel.updateScreenState(MainScreenState.MAIN)
                        }else{
                            viewModel.updateScreenState(MainScreenState.ONBOARDING)
                        }

                        if(screenState.value  == MainScreenState.ONBOARDING){
                            Scaffold {
                                Box(Modifier.padding(it)) {
                                    OnBoardingView()
                                }
                            }
                        }else{
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
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
        Greeting("Android")
    }
}