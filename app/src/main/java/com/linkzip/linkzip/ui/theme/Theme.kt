package com.linkzip.linkzip.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun LinkZipTheme(
    typography: LinkZipTypography = LinkZipTheme.typography,
    colors: LinkZipColorScheme = LinkZipTheme.color,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.green.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val currentColor = remember { colors }
    //변경에 recomposition이 최소화되기위해 부분적인 업데이트가 필요하다.
    val rememberedColors =
        remember { currentColor.copy() }.apply { updateColorSchemeFrom(currentColor) }

    CompositionLocalProvider(
        LocalLinkZipColor provides rememberedColors,
        LocalLinkZipTypography provides typography
    ) {
        ProvideTextStyle(typography.blackBold22, content = content)
    }

}

object LinkZipTheme {
    val color: LinkZipColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalLinkZipColor.current

    val typography: LinkZipTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalLinkZipTypography.current

}

