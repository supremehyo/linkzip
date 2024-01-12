package com.linkzip.linkzip.presentation.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.R
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun LinkGroupComponent(
    linkGroupName : String,
    linkGroupIcon : Int,
    linkGroupColor : Color,
    linkGroupId : Long,
    clickAction : (Long) -> Unit
){
    Column(
        modifier = Modifier.padding(
            vertical = 10.dp
        )
    ) {
        Card(
            modifier= Modifier
                .clickable {
                    clickAction(linkGroupId)
                }
                .width(316.dp)
                .height(80.dp)
                .background(Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = linkGroupColor
            )
        ){
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp)
                        .padding(end = 8.dp),
                    painter = painterResource(id = linkGroupIcon),
                    contentDescription = "groupIcon")
                Text(text = linkGroupName,
                    style = LinkZipTheme.typography.semiBold16, color = LinkZipTheme.color.black)
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.icon_arrow_right),
                    contentDescription = "rightArrow")
            }


        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
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