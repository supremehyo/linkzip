package com.linkzip.linkzip.presentation.feature.onboarding

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.presentation.feature.home.HomeView
import com.linkzip.linkzip.presentation.feature.main.Greeting
import com.linkzip.linkzip.ui.theme.LinkZipColorScheme
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.ui.theme.LinkZipTypography

sealed class OnboardingPath(val path: String) {
    object Main : OnboardingPath("MAIN")
}

val pageTitle = listOf(
    "링크 그룹 만들기",
    "링크 즐겨찾기",
    "내 모든 링크를\n쉽게 관리해볼까요?"
)

val pageContent = listOf(
    "내가 분류하고 싶은 대로 그룹을 만들 수 있어요",
    "자주 방문하는 링크는 빠르게 볼 수 있어요",
    ""
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingView() {
    val navController = rememberNavController()
    val pagerState = rememberPagerState(pageCount = { 3 })
    Scaffold(
        modifier = Modifier
            .padding(top = 115.dp)
            .fillMaxHeight(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Log.e("Sdfsdfsdfsf", "22")
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
                        text = pageTitle[page],
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = LinkZipTypography.textStyle.blackBold22.copy(
                            textAlign = TextAlign.Center
                        )

                    )
                    Text(
                        text = pageContent[page],
                        style = LinkZipTypography.textStyle.blackMedium14
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

                }
            ){
                Text("시작하기",
                    style = LinkZipTypography.textStyle.whiteMedium16)
            }
        }


        //navController.navigate(OnboardingPath.Main.path)


        /*
    NavHost(
        navController =  navController,
        startDestination = OnboardingPath.Main.path){
        composable(OnboardingPath.Main.path) {
            HomeView()
        }
    }

     */
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
        OnBoardingView()
    }
}
