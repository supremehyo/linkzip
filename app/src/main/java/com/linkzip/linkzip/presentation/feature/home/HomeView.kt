package com.linkzip.linkzip.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.HomeNavigation

@Composable
fun HomeView(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
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
                ClickableText(text = AnnotatedString(stringResource(R.string.all)),
                    modifier = Modifier.padding(end = 24.dp),
                    style = if (screenState == HomeScreenState.ALL) {
                        LinkZipTheme.typography.normal20.copy(color = LinkZipTheme.color.wg70)
                    } else {
                        LinkZipTheme.typography.normal20.copy(color = LinkZipTheme.color.wg40)
                    },
                    onClick = {
                        homeViewModel.updateHomeScreenState(HomeScreenState.ALL)
                    }
                )
                ClickableText(text = AnnotatedString(stringResource(R.string.favorite_link)),
                    modifier = Modifier.padding(end = 24.dp),
                    style = if (screenState == HomeScreenState.ALL) {
                        LinkZipTheme.typography.normal20.copy(color = LinkZipTheme.color.wg40)
                    } else {
                        LinkZipTheme.typography.normal20.copy(color = LinkZipTheme.color.wg70)
                    },
                    onClick = {
                        homeViewModel.updateHomeScreenState(HomeScreenState.FAVORITE)
                    })
            }
            HomeNavigation(mainViewModel)
        }
    }
}