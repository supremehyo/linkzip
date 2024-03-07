package com.linkzip.linkzip.util

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShareButton(shareText: String) {
    val context = LocalContext.current

    // 버튼 클릭 시 실행될 액션 정의
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    Button(onClick = { context.startActivity(Intent.createChooser(shareIntent, null)) }) {
        Text("공유하기")
    }
}
