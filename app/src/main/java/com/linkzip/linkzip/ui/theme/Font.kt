package com.linkzip.linkzip.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.linkzip.linkzip.R
import org.w3c.dom.Text

val pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
)

/**
 * 변수를 꼭 추가해서 사용해주세요 !
 * color_weight_size 순서로 선언해주세요
 * 실제 사용할 떄는 "LinkZipTheme.typography.blackBold22"
 */
@Immutable
data class LinkZipTypography(
    val blackBold22 : TextStyle,
    val blackMedium14 : TextStyle,
    val whiteMedium16 : TextStyle
) {
    companion object {
        val textStyle = LinkZipTypography(
            blackBold22 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            blackMedium14 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            whiteMedium16 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
        )
    }
}


val LocalLinkZipTypography = staticCompositionLocalOf {
    LinkZipTypography(
        blackBold22 = LinkZipTypography.textStyle.blackBold22,
        blackMedium14 = LinkZipTypography.textStyle.blackMedium14,
        whiteMedium16 = LinkZipTypography.textStyle.whiteMedium16
    )
}