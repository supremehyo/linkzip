package com.linkzip.linkzip.presentation.component

import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import com.linkzip.linkzip.R
import com.linkzip.linkzip.ui.theme.LinkZipTheme


@Composable
fun LinkGroupComponent(
    linkGroupName : String,
    linkGroupIcon : Int,
    linkGroupColor : Color,
    linkGroupId : Long,
    modifier: Modifier? =null,
    clickAction : (Long) -> Unit
){
    Column(
        modifier = (modifier ?: Modifier)
            .padding(
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroduceComponent(
    isDimmed : (Boolean)->Unit
) {
    var offsetX by remember { mutableStateOf(0.dp) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan ,_,_->
                    val translationX = pan.x
                    onLeftSwipe(translationX){ it->
                        if ((offsetX *-1) > 80.dp) {
                            offsetX = (offsetX + it).coerceAtLeast((-81).dp)
                            isDimmed(true)
                        } else {
                            offsetX = (offsetX + it).coerceAtMost(0.dp)
                            isDimmed(false)
                        }
                    }
                }
            }
    ) {
        LinkGroupComponent(
            "만나서 반가워요\n링크zip을 소개할게요!",
            R.drawable.guide_image,
            LinkZipTheme.color.orangeFFE6C1,
            1L,
            modifier = Modifier.offset(x = offsetX)
        ){

        }
    }
}

private fun onLeftSwipe(translationX: Float,callback : (Dp)->Unit) {
    if (translationX < 0) {
        callback((translationX * 0.8).dp)
    }else{
        callback((translationX * 0.8).dp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {

    }
}