package com.linkzip.linkzip.presentation.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.HomeNavigation

@Composable
fun HomeView (){
    Surface(
        color = LinkZipTheme.color.wg50
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HomeNavigation()
        }
    }

}