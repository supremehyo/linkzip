package com.linkzip.linkzip.presentation.feature.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.R
import com.linkzip.linkzip.presentation.feature.onboarding.OnBoardingView
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.ui.theme.LinkZipTypography

@Composable
fun MyPageView(){
    Column(
        modifier = Modifier.padding(horizontal = 22.dp)
    ) {
        Text(text = stringResource(R.string.my_page_title),
            style = LinkZipTypography.textStyle.bold20,
            modifier = Modifier.padding(vertical = 40.dp))
        Box(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LinkZipTheme.color.greenD4F0EB)
            .height(80.dp),
        ){
            Row(
                modifier = Modifier.padding(horizontal = 21.dp, vertical = 21.dp)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.myPage_button_title_1),
                        style = LinkZipTypography.textStyle.medium12
                    )
                    Spacer(modifier = Modifier.height(height = 5.dp))
                    Text(
                        text = stringResource(R.string.myPage_button_sub_title_1),
                        style = LinkZipTypography.textStyle.bold16
                    )
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(bottom = 40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LinkZipTheme.color.orangeFFEEB1)
            .height(80.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
        MyPageView()
    }
}

