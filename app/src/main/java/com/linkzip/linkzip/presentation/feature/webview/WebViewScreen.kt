package com.linkzip.linkzip.presentation.feature.webview

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.linkzip.linkzip.util.BackHandler
import com.linkzip.linkzip.util.DisposableEffectWithLifeCycle
import com.linkzip.linkzip.util.HandleBackButtonAction
import kotlinx.coroutines.launch

@Composable
fun WebViewScreen(
    onBackButtonPressed: () -> Unit,
    linkUrl : String?
) {
    HandleBackButtonAction{
        onBackButtonPressed()
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

@Composable
fun OpenBrowser(
    url: String,
    onBackButtonPressed: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(true){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
    DisposableEffectWithLifeCycle(
        onStart = {

        },
        onResume = {
            // 부르고 싶은 것
            onBackButtonPressed()
        }
    )
}
