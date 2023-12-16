package com.linkzip.linkzip.presentation.component
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.linkzip.linkzip.R
import com.linkzip.linkzip.ui.theme.LinkZipTheme


@Composable
fun BottomDialogComponent(
    onDismissRequest: () -> Unit,
    properties : DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
){
    Box(modifier =
    Modifier
        .fillMaxWidth(1f)
        .padding(horizontal = 22.dp)
        .padding(top = 30.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(LinkZipTheme.color.wg10),
        contentAlignment = Alignment.Center
    ){
        content()
    }
}

@Composable
fun BottomDialogMenuComponent(
    menuImage : Int,
    menuTitle : String,
    onClickAction : (String) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(bottom =28.dp)
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
        Text(text = menuTitle,
            style = LinkZipTheme.typography.medium18, color = LinkZipTheme.color.black)
    }

}

@Preview(showBackground = true)
@Composable
fun BottomDialogComponentPreview() {
    LinkZipTheme {
        BottomDialogComponent(
            onDismissRequest = {

            }
        ){
            LazyColumn(){
                items(5){ it ->
                    BottomDialogMenuComponent(
                        menuImage = R.drawable.guide_image,
                        menuTitle = "테스트$it"
                    ){
                        Log.v("menuClick","$it")
                    }
                }
            }
        }
    }
}