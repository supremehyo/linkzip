package com.linkzip.linkzip.presentation.feature.home.favorite

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.component.LinkGroupComponent
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.navigation.HomeNavigation
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun FavoriteView (
    homeViewModel: HomeViewModel =  hiltViewModel()
){
    val favoriteLinkList by homeViewModel.favoriteListFlow.collectAsStateWithLifecycle(null)
    homeViewModel.getFavoriteLink()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if(favoriteLinkList!=null){
            FavoriteLinkList(favoriteLinkList!!)
        }else{
            Column(
                modifier = Modifier.fillMaxSize().padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .width(45.dp)
                        .height(42.dp),
                    painter = painterResource(id = R.drawable.empty_link),
                    contentDescription = "empty_link")
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.empty_favorite),
                    style = LinkZipTheme.typography.medium16.copy(color = LinkZipTheme.color.wg40),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}


enum class DragValue { Start, Center, End }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeScreen(
    contentComposable: @Composable () -> Unit,
    buttonComposable: @Composable () -> Unit,
    buttonModifier : Modifier,
    clickAction : () -> Unit
) {
    val density = LocalDensity.current
    val screenSizeDp = LocalConfiguration.current.screenWidthDp.dp
    val screenSizePx = with(density) { screenSizeDp.toPx() }
    var buttonVisible by remember { mutableStateOf(false) }
    val buttonAlpha by animateFloatAsState(targetValue = if (buttonVisible) 1f else 0f, label = "",)
    val anchors = remember {
        DraggableAnchors {
            DragValue.Start at 0f
            DragValue.End at -250f
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

@Composable
fun FavoriteLinkList(
    list : UiState<List<LinkData>>
){
    when(list){
        is UiState.Success ->{
            LazyColumn(){
                items(list.data){ group ->
                   
                }
            }
        }
        is UiState.Loding -> {

        }
        is UiState.Error -> {
            
        }
    }
}