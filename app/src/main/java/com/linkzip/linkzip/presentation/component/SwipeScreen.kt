package com.linkzip.linkzip.presentation.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.presentation.feature.home.favorite.DragValue
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeScreen(
    contentComposable: @Composable () -> Unit,
    buttonComposable: @Composable () -> Unit,
    buttonModifier : Modifier,
    enable : Boolean = true,
    clickAction : () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val anchors = remember {
        DraggableAnchors {
            DragValue.Start at 0f
            DragValue.End at if(enable) -250f else 0f
        }
    }
    val state = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Start,
            positionalThreshold = {  with(density) { 130.dp.toPx() } },
            velocityThreshold = {  with(density) { 130.dp.toPx() } },
            animationSpec = tween(),
        )
    }
    SideEffect { state.updateAnchors(anchors) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.height(100.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .anchoredDraggable(state, Orientation.Horizontal)
        ) {
            Box(modifier = Modifier
                .width(80.dp)
                .height(78.dp)
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(12.dp))
                .background(color = LinkZipTheme.color.redFB5B63)
                .padding(
                    vertical = 10.dp
                )
                .clickable {

                },
            ) {
                Box(modifier = buttonModifier
                    .align(Alignment.Center)
                    .clickable {
                        coroutineScope.launch {
                            state.snapTo(DragValue.Start)
                        }
                        clickAction()
                    }){
                    buttonComposable()
                }
            }
            Box(
                Modifier
                    .offset {
                        IntOffset(
                            x = state
                                .requireOffset()
                                .roundToInt(),
                            y = 0
                        )
                    } // x = 0 Horizontal
            ){
                contentComposable()
            }
        }
    }
}