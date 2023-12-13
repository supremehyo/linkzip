package com.linkzip.linkzip.presentation.feature.onboarding

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.feature.main.MainViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.ui.theme.LinkZipTypography

sealed class OnboardingPath(val path: String) {
    object Main : OnboardingPath("MAIN")
}

val pageTitle = listOf(
    R.string.onboarding_title_1,
    R.string.onboarding_title_2,
    R.string.onboarding_title_3
)

val pageContent = listOf(
    R.string.onboarding_content_1,
    R.string.onboarding_content_2,
    R.string.onboarding_content_3
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingView(
    mainViewModel: MainViewModel = hiltViewModel(),
    onClick : () -> Unit
) {
    val navController = rememberNavController()
    val pagerState = rememberPagerState(pageCount = { 3 })
    Scaffold(
        modifier = Modifier
            .padding(top = 115.dp)
            .fillMaxHeight(),
    ) { innerPadding->
        Column(
            modifier = Modifier.padding(innerPadding).background(LinkZipTheme.color.wg10),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                // Our page content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(bottom = 37.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text =  stringResource(pageTitle[page]),
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = LinkZipTypography.textStyle.bold22.copy(
                            textAlign = TextAlign.Center
                        ),
                        color = LinkZipTheme.color.black

                    )
                    Text(
                        text = stringResource(pageContent[page]),
                        style = LinkZipTypography.textStyle.medium14,
                        color = LinkZipTheme.color.black
                    )
                }
            }
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .offset(y = 88.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }

        if(pagerState.currentPage == 2){
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .height(55.dp)
                    .offset(y = 645.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LinkZipTheme.color.wg70
                ),
                onClick = {
                    onClick()

                }
            ){
                Text("시작하기",
                    style = LinkZipTypography.textStyle.medium16, color = LinkZipTheme.color.white)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {

    }
}
