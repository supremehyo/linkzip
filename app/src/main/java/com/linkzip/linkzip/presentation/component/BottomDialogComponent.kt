package com.linkzip.linkzip.presentation.component
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.model.HomeBottomDialogMenu
import com.linkzip.linkzip.ui.theme.LinkZipTheme


@Composable
fun BottomDialogComponent(
    onDismissRequest: () -> Unit,
    visible: Boolean,
    height : Dp,
    content: @Composable () -> Unit
) {
    if (visible) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.Inherit
            )
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .offset(y = (-30).dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bottom_dialog_top),
                        contentDescription = "bottom_dialog_top")
                    Box(modifier = Modifier
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