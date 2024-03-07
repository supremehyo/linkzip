package com.linkzip.linkzip.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.ui.theme.LinkZipTheme

enum class FieldSize {
    NORMAL, LARGE
}

@Composable
fun CommonEditTextField(
    title: String,
    initialText: String = "",
    fieldType: FieldSize,
    maxLength: Int = 12,
    textCountOption: Boolean,
    resultText: (Pair<String, String>) -> Unit,
    hintText: String,
    isFocus: (Boolean) -> Unit
) {
    var groupNameText by remember { mutableStateOf(TextFieldValue(text = initialText, selection = TextRange(initialText.length))) }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(initialText) {
        groupNameText = TextFieldValue(text = initialText, selection = TextRange(initialText.length))
    }

    var height = when (fieldType) {
        FieldSize.NORMAL -> 50.dp
        FieldSize.LARGE -> 162.dp
    }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                isFocus(isFocused)
            },
        value = groupNameText,
        onValueChange = {
            groupNameText = it
            resultText(Pair(title, groupNameText.text))
        },
        textStyle = LinkZipTheme.typography.medium16,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LinkZipTheme.color.wg10, shape = RoundedCornerShape(size = 12.dp))
                    .border(width = 1.dp, color = if (isFocused) LinkZipTheme.color.wg40 else LinkZipTheme.color.wg10, shape = RoundedCornerShape(size = 12.dp))
                    .padding(vertical = 16.dp, horizontal = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (groupNameText.text.isEmpty()) {
                    if(!isFocused){
                        Text(
                            text = hintText,
                            style = LinkZipTheme.typography.medium16.copy(color = LinkZipTheme.color.wg40)
                        )
                    }
                }

                innerTextField()

                if (groupNameText.text.isNotEmpty() && textCountOption) {
                    Text(
                        text = "${groupNameText.text.count()}/$maxLength",
                        style = LinkZipTheme.typography.medium12.copy(color = LinkZipTheme.color.wg40)
                    )
                }
            }
        }
    )
}

