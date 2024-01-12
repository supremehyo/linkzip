package com.linkzip.linkzip.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.presentation.feature.main.Greeting
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun DropDownMenuComponent() {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    //else LinkZipTheme.color.wg10,
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LinkZipTheme.color.wg10,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .border(
                width = 1.dp,
                color = LinkZipTheme.color.wg40,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(vertical = 16.dp, horizontal = 14.dp)
            .clickable {
                isDropDownMenuExpanded = true
            },
    ) {
        DropdownMenu(
            expanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false }) {
            DropdownMenuItem(text = {

            }, onClick = {

            })
        }

        Text(text = "그룹을 선택해주세요")

    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuPreview() {
    LinkZipTheme {
        DropDownMenuComponent()
    }
}