package com.linkzip.linkzip.presentation.feature.home.all

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.linkzip.linkzip.R
import com.linkzip.linkzip.presentation.component.LinkGroupComponent
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun AllView (){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(){
            items(5){
                LinkGroupComponent(
                    "만나서 반가워요\n링크zip을 소개할게요!",
                    R.drawable.guide_image,
                    LinkZipTheme.color.orangeFFE6C1,
                    1L
                ){ it ->
                    Log.e("groupClick" , "$it")
                }
            }
        }
        Text(
            text = stringResource(R.string.add_group),
            style = LinkZipTheme.typography.normal16,
            color = LinkZipTheme.color.wg50
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
        AllView()
    }
}