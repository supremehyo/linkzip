package com.linkzip.linkzip.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun DialogComponent(
    onDismissRequest: () -> Unit,
    visible: Boolean,
    cancelButtonText : String,
    successButtonText : String,
    content : String,
    onClickEvent : () -> Unit
){
    if (visible) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.Inherit
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(153.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().background(LinkZipTheme.color.white)
                ){
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = content,
                            style = LinkZipTheme.typography.bold18.copy(
                                color = LinkZipTheme.color.black
                            ),
                            modifier = Modifier.padding(vertical = 32.dp)
                        )
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ){
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(0.48f)
                                    .padding(horizontal = 0.dp)
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                onClick = {
                                    onDismissRequest()
                                },
                                colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = LinkZipTheme.color.wg20
                                ),
                                shape = MaterialTheme.shapes.small.copy(all = CornerSize(0.dp))
                            ){
                                Text(text = cancelButtonText, style = LinkZipTheme.typography.medium16.copy(LinkZipTheme.color.wg70))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .padding(horizontal = 0.dp)
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                onClick = {
                                          onClickEvent()
                                },
                                colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = LinkZipTheme.color.black
                                ),
                                shape = MaterialTheme.shapes.small.copy(all = CornerSize(0.dp))
                            ){
                                Text(text = successButtonText , style = LinkZipTheme.typography.medium16.copy(LinkZipTheme.color.white))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogComponentPreView() {
    var showDialog by remember { mutableStateOf(true) }
    LinkZipTheme {
        DialogComponent(
            onDismissRequest = { showDialog = false },
            visible = true,
            cancelButtonText = "취소",
            successButtonText = "붙여넣기",
            content = "복사한 링크를 붙여넣을까요?",
            onClickEvent = {

            }
        )
    }
}