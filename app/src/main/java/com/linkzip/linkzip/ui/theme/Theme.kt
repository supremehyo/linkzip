package com.linkzip.linkzip.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun LinkZipTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = getCurrentThemeColor(ThemeColor.Default, darkTheme)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.wg10.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CustomTheme(
        colorScheme = colorScheme,
        typography = LinkZipTheme.typography,
        content = content
    )
}

@Composable
fun CustomTheme(
    colorScheme: LinkZipColorScheme,
   // shapes: Shapes,
    typography: LinkZipTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider {
        LocalLinkZipTypography provides typography
        LocalLinkZipColor provides colorScheme
    }
}

object LinkZipTheme {
    val color: LinkZipColorScheme
        @Composable
        get() = LocalLinkZipColor.current
    val typography: LinkZipTypography
        @Composable
        get() = LocalLinkZipTypography.current

    //TODO 이런식으로 drawable 도 만들어줘야합니다 !
//    val shape: Shapes
//        @Composable
//        get() = LocalLinkZipShapes.current
}
