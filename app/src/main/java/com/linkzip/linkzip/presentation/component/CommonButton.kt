package com.linkzip.linkzip.presentation.component

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun CommonButton(
    buttonName : String,
    enable : Boolean,
    buttonCompleteName : String? = null,
    keyBoardUpOption : Boolean? = null,
    onClickEvent : () -> Unit
) {
    var isEnabled by remember { mutableStateOf(enable) }
    var isKeyBoardUp by remember { mutableStateOf(keyBoardUpOption) }

    if(isKeyBoardUp == true){
        Log.v("sdfsdfsdf" , "tttt")
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp)
                .padding(vertical = 16.dp)
                .height(55.dp),
            onClick = { isEnabled = !isEnabled },
            colors =
            ButtonDefaults.buttonColors(
                containerColor = if(enable) LinkZipTheme.color.wg70 else LinkZipTheme.color.wg20
            ),
            enabled = isEnabled
        ) {
            Text(
                text = if(buttonCompleteName != null && isEnabled) buttonCompleteName else buttonName,
                style = LinkZipTheme.typography.medium16.copy(color =
                if(enable) LinkZipTheme.color.white else LinkZipTheme.color.wg40
                )
            )
        }
    }else{
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(vertical = 16.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(12.dp)),
            onClick = { isEnabled = !isEnabled },
            colors =
            ButtonDefaults.buttonColors(
                containerColor = if(enable) LinkZipTheme.color.wg70 else LinkZipTheme.color.wg20
            ),
            enabled = isEnabled
        ) {
            Text(
                text = if(buttonCompleteName != null && isEnabled) buttonCompleteName else buttonName,
                style = LinkZipTheme.typography.medium16.copy(color =
                if(enable) LinkZipTheme.color.white else LinkZipTheme.color.wg40
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonButtonPreview() {
    LinkZipTheme {
        CommonButton(
            enable = false,
            keyBoardUpOption = true,
            buttonName = "저장하기",
            onClickEvent = {

            }
        )
    }
}