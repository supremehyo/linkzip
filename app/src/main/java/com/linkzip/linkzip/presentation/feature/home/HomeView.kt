package com.linkzip.linkzip.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.presentation.feature.main.Greeting
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.HomeNavigation
import com.linkzip.linkzip.util.navigation.MainBottomNavigation
import com.linkzip.linkzip.util.navigation.MainBottomPath

@Composable
fun HomeView (
    homeViewModel: HomeViewModel =  hiltViewModel()
){
    val screenState by homeViewModel.homeScreenState.collectAsState(initial = HomeScreenState.ALL)
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 22.dp)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.padding(bottom = 28.dp)
            ) {
                Text(text = stringResource(R.string.all), modifier = Modifier.padding(end = 24.dp),
                    color = if(screenState == HomeScreenState.ALL) LinkZipTheme.color.black else LinkZipTheme.color.wg40)
                Text(text = stringResource(R.string.favorite_link),
                    color = if(screenState == HomeScreenState.ALL) LinkZipTheme.color.black else LinkZipTheme.color.wg40)
            }
            HomeNavigation()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
        HomeView()
    }
}