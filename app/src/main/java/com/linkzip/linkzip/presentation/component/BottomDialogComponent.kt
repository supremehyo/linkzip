package com.linkzip.linkzip.presentation.component
import android.view.Gravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.HomeBottomDialogMenu
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.presentation.feature.addgroup.getDrawableIcon
import com.linkzip.linkzip.ui.theme.LinkZipTheme


@Composable
fun BottomDialogComponent(
    onDismissRequest: () -> Unit,
    visible: Boolean,
    height : Dp,
    horizontalMargin: Dp,
    content: @Composable () -> Unit
) {
    if (visible) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
                securePolicy = SecureFlagPolicy.Inherit
            )
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .padding(horizontal = horizontalMargin)
                    .offset(y = (-30).dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = LinkZipTheme.color.white)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bottom_dialog_top),
                        contentDescription = "bottom_dialog_top",
                        modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                        alignment = Alignment.Center)
                    Box(modifier = Modifier
                        .background(color = LinkZipTheme.color.white)
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 30.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()){
                        content()
                    }
                }
            }
        }
    }
}

@Composable
fun BottomDialogLinkAddGroupMenuComponent(
    groupData: GroupData,
    iconData : IconData,
    onClickAction : (GroupData) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .clickable {
                onClickAction(groupData)
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .padding(end = 12.dp),
            painter = painterResource(id = getDrawableIcon(iconData.iconName)),
            contentDescription = "menu_image")
        Text(text = groupData.groupName ,
            style = LinkZipTheme.typography.medium18, color = LinkZipTheme.color.black)
    }
}


@Composable
fun BottomDialogMenuComponent(
    menuImage : Int,
    menuTitle : HomeBottomDialogMenu,
    onClickAction : (HomeBottomDialogMenu) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .clickable {
                onClickAction(menuTitle)
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .padding(end = 12.dp),
            painter = painterResource(id = menuImage),
            contentDescription = "menu_image")
        Text(text = stringResource(id = menuTitle.title),
            style = LinkZipTheme.typography.medium18, color = LinkZipTheme.color.black)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomDialogComponentPreview() {
    var showDialog by remember { mutableStateOf(true) }
    LinkZipTheme {

    }
}