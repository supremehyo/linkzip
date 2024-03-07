package com.linkzip.linkzip.presentation.feature.group

import android.util.Log
import android.view.GestureDetector
import android.webkit.WebView
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.BottomDialogMenu
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.component.BottomDialogComponent
import com.linkzip.linkzip.presentation.component.BottomDialogMenuComponent
import com.linkzip.linkzip.presentation.component.HeaderTitleView
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import okhttp3.internal.notifyAll

@Composable
fun GroupView(
    groupData: Pair<GroupData, IconData>?,
    onBackButtonPressed: () -> Unit,
    onActionButtonPressed: () -> Unit,
    groupViewModel: GroupViewModel = hiltViewModel()
) {
    val backgroundColor = groupData?.second?.iconHeaderColor
    val groupName = groupData?.first?.groupName


    val linkList by groupViewModel.linkListByGroup.collectAsStateWithLifecycle()

    LaunchedEffect(linkList) {
        groupViewModel.getLinkListByGroup(groupData?.first?.groupId ?: throw NullPointerException())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
    ) {
        HeaderTitleView(
            backgroundColor = Color(backgroundColor ?: LinkZipTheme.color.white.toArgb()),
            onBackButtonPressed = onBackButtonPressed,
            onActionButtonPressed = onActionButtonPressed,
            title = groupName ?: "error"
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextWithIcon(
                iconFile = R.drawable.icon_pin,
                message = stringResource(R.string.favorite_link)
            )
            TextWithIcon(
                iconFile = R.drawable.ic_uncheck_gray,
                message = stringResource(R.string.select_link)
            )
        }
        Box(modifier = Modifier.height(8.dp))
        LazyColumn {
            itemsIndexed(linkList) { index, data ->
                Box(modifier = Modifier.clickable {
                    Log.e("adad", "click Link TODO")
                }) {
                    LinkInGroup(data)
                }
            }
        }
    }
}


@Composable
fun TextWithIcon(iconFile: Int, message: String) {
    Row {
        Icon(
            painter = painterResource(id = iconFile),
            contentDescription = "favorite",
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = message,
            style = LinkZipTheme.typography.medium12.copy(color = LinkZipTheme.color.wg70)
        )
    }
}
val favoriteMenuItems =
    mutableListOf(
        BottomDialogMenu.ShareLink,
        BottomDialogMenu.ModifyLink,
        BottomDialogMenu.FavoriteLink,
        BottomDialogMenu.None
    )

@Composable
fun LinkInGroup(link: LinkData, groupViewModel: GroupViewModel = hiltViewModel()) {

    val favoriteMenuItems =
        mutableListOf(
            BottomDialogMenu.ShareLink,
            BottomDialogMenu.ModifyLink,
            BottomDialogMenu.FavoriteLink,
            BottomDialogMenu.None
        )

    val unFavoriteMenuItems =
        mutableListOf(
            BottomDialogMenu.ShareLink,
            BottomDialogMenu.ModifyLink,
            BottomDialogMenu.UnFavoriteLink,
            BottomDialogMenu.None
        )

    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(128.dp)
                    .height(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
                model = link.linkThumbnail,
                contentDescription = null,
            )
            Box(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = link.linkTitle,
                    style = LinkZipTheme.typography.bold14.copy(color = LinkZipTheme.color.wg70)
                )
                Box(modifier = Modifier.height(8.dp))
                Text(
                    text = "메모 추가하기",
                    style = LinkZipTheme.typography.medium12.copy(color = LinkZipTheme.color.wg50),
                    textDecoration = TextDecoration.Underline
                )
            }
        }
        IconButton(onClick = {
            showDialog = !showDialog
        }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_threedots),
                contentDescription = null
            )
        }

        val menuItems = if(link.favorite) unFavoriteMenuItems else favoriteMenuItems

        BottomDialogComponent(
            onDismissRequest = { showDialog = false },
            visible = showDialog,
            horizontalMargin = 29.5.dp
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(29.dp)
            ) {
                items(menuItems.size - 1) { it ->
                    BottomDialogMenuComponent(
                        menuItems = menuItems[it]
                    ) {
                        showDialog = false
                        when (it) {
                            BottomDialogMenu.ShareLink -> {

                            }

                            BottomDialogMenu.ModifyLink -> {

                            }

                            BottomDialogMenu.FavoriteLink, BottomDialogMenu.UnFavoriteLink -> {
                                groupViewModel.updateFavoriteLink(
                                    !link.favorite,
                                    link,
                                    success = {
                                        Log.e("adad", "success")
                                    },
                                    fail = {
                                        Log.e("adad", "fail")
                                    })
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}
