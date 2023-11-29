package com.linkzip.linkzip.presentation.feature.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.data.model.IS_FRIST
import com.linkzip.linkzip.presentation.feature.onboarding.OnBoardingView
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.MainBottomNavigation
import com.linkzip.linkzip.util.navigation.MainBottomNavigationGraph
import com.linkzip.linkzip.util.navigation.MainBottomPath
import dagger.hilt.android.AndroidEntryPoint


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
                 //   val isFirst = viewModel.checkFirstStart(spf)
                 //   Log.v("ssssss" , isFirst.toString())
                    val navController = rememberNavController()
                    val items = listOf(
                        MainBottomPath.Home,
                        MainBottomPath.MyPage
                    )
                    Log.v("ssssss" , "2")
                    Scaffold(
                        bottomBar = { MainBottomNavigation(items,navController) }
                    ) {
                        Box(Modifier.padding(it)){
                            MainBottomNavigationGraph(navController)
                        }
                    }
                    /*
                    if(!isFirst){
                        val navController = rememberNavController()
                        val items = listOf(
                            MainBottomPath.Home,
                            MainBottomPath.MyPage
                        )
                        Log.v("ssssss" , "2")
                        Scaffold(
                            bottomBar = { MainBottomNavigation(items,navController) }
                        ) {
                            Box(Modifier.padding(it)){
                                MainBottomNavigationGraph(navController)
                            }
                        }
                    }else{
                        Log.v("ssssss" , "1")
                        Scaffold {
                            Box(Modifier.padding(it)){
                                OnBoardingView()
                            }
                        }
                    }

                     */
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