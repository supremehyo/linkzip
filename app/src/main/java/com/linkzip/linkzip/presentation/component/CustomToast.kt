package com.linkzip.linkzip.presentation.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.linkzip.linkzip.ui.theme.LinkZipTheme

class CustomToast(context: Context) : Toast(context) {
    @Composable
    fun MakeText(
        message: String,
        icon: Int,
        duration: Int = LENGTH_SHORT,
    ) {
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent {
            CustomToastUtil.SetView(
                messageTxt = message,
                resourceIcon = icon,
            )
        }

        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)
        views.setViewTreeViewModelStoreOwner(LocalViewModelStoreOwner.current)

        this.duration = duration
        this.view = views
        this.show()
    }
}

object CustomToastUtil {

    @Composable
    fun SetView(
        messageTxt: String,
        resourceIcon: Int
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = LinkZipTheme.color.wg70,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(id = resourceIcon),
                contentDescription = "toastIcon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = 14.dp, end = 7.dp)
            )
            Text(
                text = messageTxt,
                style = LinkZipTheme.typography.medium12.copy(
                    color = LinkZipTheme.color.white
                )
            )
        }
    }
}