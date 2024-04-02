package com.linkzip.linkzip.presentation.feature.home.favorite

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.BottomDialogMenu
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.presentation.BaseViewModel
import com.linkzip.linkzip.presentation.component.BottomDialogComponent
import com.linkzip.linkzip.presentation.component.BottomDialogMenuComponent
import com.linkzip.linkzip.presentation.feature.group.GroupViewModel
import com.linkzip.linkzip.presentation.feature.home.HomeViewModel
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.composableActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FavoriteView(
    onClickMemoPressed: (GroupData, IconData, LinkData) -> Unit,
    onActionLinkEditPressed: (GroupData, IconData, LinkData) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    baseViewModel: BaseViewModel = composableActivityViewModel()
) {
    var pairs by remember { mutableStateOf(mutableListOf<Triple<GroupData, LinkData?,IconData>>()) }
    val favoriteLinkList by homeViewModel.favoriteListFlow.collectAsStateWithLifecycle()
    val allGroupList by baseViewModel.allGroupListFlow.collectAsStateWithLifecycle()
    val iconListFlow by baseViewModel.iconListByGroup.collectAsStateWithLifecycle()

    LaunchedEffect(allGroupList) {
        CoroutineScope(Dispatchers.IO).launch {
            allGroupList.let { list ->
                baseViewModel.getIconListById(list.map { it.groupIconId })
            }
        }
    }

    LaunchedEffect(true) {
        homeViewModel.getFavoriteLink()
    }

    LaunchedEffect(iconListFlow , favoriteLinkList) {
        iconListFlow.let { iconList ->
            val tempPairs = mutableListOf<Triple<GroupData, LinkData?, IconData>>()
            val groupMap = allGroupList?.associateBy { it.groupId } as? HashMap<Long, GroupData> ?: hashMapOf()

            withContext(Dispatchers.IO) {
                favoriteLinkList.forEach { linkData ->
                    groupMap[linkData.linkGroupId.toLong()]?.let { groupData ->
                        iconList.find { it.iconId == groupData.groupIconId }?.let { icon ->
                            tempPairs.add(Triple(groupData, linkData, icon))
                        } ?: Log.e("Error", "${groupData.groupName}")
                    }
                }
                withContext(Dispatchers.Main) {
                    pairs = tempPairs
                }
            }
        }
    }



    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (favoriteLinkList != null) {
            FavoriteLinkList(pairs!!,onActionLinkEditPressed,onClickMemoPressed)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .width(45.dp)
                        .height(42.dp),
                    painter = painterResource(id = R.drawable.empty_link),
                    contentDescription = "empty_link"
                )
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


@Composable
fun FavoriteLinkList(
    pairs: List<Triple<GroupData, LinkData?,IconData>>,
    onActionLinkEditPressed: (GroupData, IconData, LinkData) -> Unit,
    onClickMemoPressed: (GroupData, IconData, LinkData) -> Unit
) {
    val groupedPairs: Map<String, List<Triple<GroupData, LinkData?,IconData>>> = pairs.groupBy { it.first.groupName }

    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        groupedPairs.forEach { (groupName, links) ->
            item {
                BasicText(text = groupName, style = LinkZipTheme.typography.bold18)
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(links) { pair ->
                Column {
                    FavoriteLinkComponent(pair,onClickMemoPressed,onActionLinkEditPressed)
                }
            }
        }
    }
}

@Composable
fun FavoriteLinkComponent(
    data: Triple<GroupData, LinkData?,IconData>,
    onClickMemoPressed: (GroupData, IconData, LinkData) -> Unit,
    onActionLinkEditPressed: (GroupData, IconData, LinkData) -> Unit) {
    Column {
        LinkInFavorite(
            pair = data,
            onClickMemoPressed =onClickMemoPressed,
            onActionLinkEditPressed = onActionLinkEditPressed
        )
    }

}

@Composable
fun LinkInFavorite(
    pair: Triple<GroupData, LinkData?,IconData>,
    onClickMemoPressed: (GroupData, IconData, LinkData) -> Unit,
    onActionLinkEditPressed: (GroupData, IconData, LinkData) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    groupViewModel: GroupViewModel = hiltViewModel(),
    baseViewModel: BaseViewModel = composableActivityViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


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
                model = pair.second?.linkThumbnail,
                contentDescription = null,
            )
            Box(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = pair.second!!.linkTitle,
                    style = LinkZipTheme.typography.bold14.copy(color = LinkZipTheme.color.wg70)
                )
                Box(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.clickable {
                        onClickMemoPressed.invoke(pair.first, pair.third ,pair.second!!)
                    },
                    text = "메모 추가하기",
                    style = LinkZipTheme.typography.medium12.copy(color = LinkZipTheme.color.wg50),
                    textDecoration = TextDecoration.Underline,
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

        val menuItems = if (pair.second!!.favorite) unFavoriteMenuItems else favoriteMenuItems

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
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, pair.second!!.link)
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(shareIntent, null))
                            }

                            BottomDialogMenu.ModifyLink -> {
                                onActionLinkEditPressed(pair.first, pair.third ,pair.second!!)
                            }

                            BottomDialogMenu.FavoriteLink , BottomDialogMenu.UnFavoriteLink-> {
                                groupViewModel.updateFavoriteLink(
                                    pair.second!!,
                                    success = {
                                        baseViewModel.getAllGroups()
                                        homeViewModel.getFavoriteLink() // 즐겨찾기 목록을 갱신
                                    },
                                    fail = {

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

@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {
    LinkZipTheme {

    }
}