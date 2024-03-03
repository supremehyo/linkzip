package com.linkzip.linkzip.presentation.feature.main

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAppVersion(applicationContext)
        setContent {
            val homeViewModel: HomeViewModel = hiltViewModel()
            LinkZipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LinkZipTheme.color.white
                ) {
                    val spf = getSharedPreferences("spf", MODE_PRIVATE)
                    val isFirst = viewModel.checkFirstStart(spf)

                    if (!isFirst) {
                        viewModel.updateScreenState(MainScreenState.MAIN.state)
                    } else {
                        viewModel.updateScreenState(MainScreenState.ONBOARDING.state)
                    }
                    MainNavigation()
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