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
 * weight_size 순서로 선언해주세요
 * 실제 사용할 떄는 "LinkZipTheme.typography.blackBold22"
 */
@Immutable
data class LinkZipTypography(
    val bold22: TextStyle,
    val bold20 : TextStyle,
    val bold18 : TextStyle,
    val bold16 : TextStyle,
    val semiBold10 : TextStyle,
    val medium12 : TextStyle,
    val medium14 : TextStyle,
    val medium16 : TextStyle,
    val medium18 : TextStyle,
    val semiBold16 : TextStyle,
    val normal16 : TextStyle,
    val normal20: TextStyle
) {
    companion object {
        val textStyle = LinkZipTypography(
            bold22 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            bold20 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            bold16 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            medium12 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            medium14 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            medium16 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
            semiBold16 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                )
            ),
            normal16 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                )
            ),
            normal20 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                )
            ),
            semiBold10 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                )
            ),
            medium18 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                )
            ),
            bold18 = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                )
            )
        )
    }
}


val LocalLinkZipTypography = staticCompositionLocalOf {
    LinkZipTypography(
        bold22 = LinkZipTypography.textStyle.bold22,
        medium12 = LinkZipTypography.textStyle.medium12,
        medium14 = LinkZipTypography.textStyle.medium14,
        medium16 = LinkZipTypography.textStyle.medium16,
        semiBold16 = LinkZipTypography.textStyle.semiBold16,
        normal16 = LinkZipTypography.textStyle.normal16,
        normal20 = LinkZipTypography.textStyle.normal20,
        semiBold10 = LinkZipTypography.textStyle.semiBold10,
        medium18 = LinkZipTypography.textStyle.medium18,
        bold18 = LinkZipTypography.textStyle.bold18,
        bold20 = LinkZipTypography.textStyle.bold20,
        bold16 = LinkZipTypography.textStyle.bold16
    )
}