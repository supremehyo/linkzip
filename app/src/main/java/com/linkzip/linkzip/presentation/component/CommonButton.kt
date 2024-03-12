package com.linkzip.linkzip.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.ui.theme.LinkZipTheme

//버튼 제대로 움직이는지 검증 필요
@Composable
fun CommonButton(
    buttonName : String,
    enable : Boolean,
    buttonColor : Color,
    isFocused: Boolean= false,
    buttonCompleteName : String? = null,
    keyBoardUpOption : Boolean? = null,
    onClickEvent : () -> Unit
) {
    var isEnabled by remember { mutableStateOf(enable) }
    var isKeyBoardUp by remember { mutableStateOf(keyBoardUpOption) }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (isFocused) 0.dp else 19.dp)
            .background(if(enable) buttonColor else LinkZipTheme.color.wg20)
            .height(55.dp),
        shape = if (isFocused) RoundedCornerShape(0.dp) else RoundedCornerShape(12.dp),
        onClick = { isEnabled = !isEnabled
            onClickEvent()},
        enabled = isEnabled,
    ) {
        Text(
            text = if(buttonCompleteName != null && isEnabled) buttonCompleteName else buttonName,
            style = LinkZipTheme.typography.medium16.copy(color =
            if(enable) LinkZipTheme.color.white else LinkZipTheme.color.wg40
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommonButtonPreview() {
}