package com.linkzip.linkzip.presentation.feature.webview

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.util.BackHandler
import kotlinx.coroutines.launch

@Composable
fun WebViewScreen(
    linkUrl : String?
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = true) {
        coroutineScope.launch {
            //뒤로가기 처리
        }
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            linkUrl?.let {
                if(linkUrl.isNotEmpty()) webView.loadUrl("$linkUrl") else webView.loadUrl("https://naver.com")
            }
        }
    )
}