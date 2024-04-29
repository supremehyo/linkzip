package com.linkzip.linkzip.presentation.feature.my


import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.linkzip.linkzip.R
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.ui.theme.LinkZipTypography

@Composable
fun MyPageView(
    activity: Activity,
    onMove :(String) ->Unit,
    appVersion : String,
    viewModel: MyPageViewModel = hiltViewModel()
){
    val reviewManager = remember { ReviewManagerFactory.create(activity) }

    Column(
        modifier = Modifier.padding(horizontal = 22.dp)
    ) {
        Text(text = stringResource(R.string.my_page_title),
            style = LinkZipTypography.textStyle.bold20.copy(color = LinkZipTheme.color.wg70),
            modifier = Modifier.padding(vertical = 40.dp))
        Box(modifier = Modifier
            .fillMaxWidth(  )
            .clip(RoundedCornerShape(12.dp))
            .background(color = LinkZipTheme.color.greenD4F0EB)
            .height(82.dp),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 21.dp, vertical = 21.dp)
                    .clickable {
                        onMove("GUIDE")
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.myPage_button_title_1),
                        style = LinkZipTypography.textStyle.medium12.copy(color = LinkZipTheme.color.wg60)
                    )
                    Box(modifier = Modifier.height(height = 5.dp))
                    Text(
                        text = stringResource(R.string.myPage_button_sub_title_1),
                        style = LinkZipTypography.textStyle.bold16.copy(color = LinkZipTheme.color.wg60)
                    )
                }
                Image(
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.icon_arrow_right),
                    contentDescription = "rightArrow")
            }
        }
        Box(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(bottom = 40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LinkZipTheme.color.orangeFFEEB1)
            .height(82.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 21.dp, vertical = 21.dp)
                    .clickable {
                        val request = reviewManager.requestReviewFlow()
                        request.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // 리뷰 요청이 성공적으로 처리된 경우
                                val reviewInfo = task.result
                                val flow = reviewInfo?.let {
                                    reviewManager.launchReviewFlow(activity, it)
                                }
                                flow?.addOnCompleteListener {
                                    // 리뷰 작성이 완료되거나 사용자가 창을 닫은 후의 처리
                                    // 일반적으로 사용자에게 감사 메시지를 보여주거나, 로깅을 할 수 있습니다.
                                }
                            } else {
                                // 리뷰 요청 실패 처리
                                // 실패 원인에 대한 로깅을 하거나 사용자에게 오류 메시지를 보여줄 수 있습니다.
                            }
                        }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.myPage_button_title_2),
                        style = LinkZipTypography.textStyle.medium12.copy(color = LinkZipTheme.color.wg60)
                    )
                    Box(modifier = Modifier.height(height = 5.dp))
                    Text(
                        text = stringResource(R.string.myPage_button_sub_title_2),
                        style = LinkZipTypography.textStyle.bold16.copy(color = LinkZipTheme.color.wg60)
                    )
                }
                Image(
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.icon_arrow_right),
                    contentDescription = "rightArrow")
            }
        }
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(LinkZipTheme.color.wg10)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(height = 60.dp)
            .clickable {
                onMove("USE")
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "이용약관" , style = LinkZipTheme.typography.medium16.copy(LinkZipTheme.color.wg60))
            Image(
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.icon_arrow_right),
                contentDescription = "rightArrow")
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(height = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "버전정보" , style = LinkZipTheme.typography.medium16.copy(LinkZipTheme.color.wg60))
            Text(text = appVersion , style = LinkZipTheme.typography.medium16.copy(LinkZipTheme.color.wg60))
        }
    }
}


@Composable
fun rememberReviewTask(reviewManager: ReviewManager): ReviewInfo? {
    var reviewInfo: ReviewInfo? by remember {
        mutableStateOf(null)
    }
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            reviewInfo = it.result
        }else{

        }
    }

    return reviewInfo
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {

    }
}

