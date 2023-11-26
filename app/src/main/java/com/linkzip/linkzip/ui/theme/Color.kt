package com.linkzip.linkzip.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

/**
 * 변수를 꼭 추가해서 사용해주세요 !
 * color_hexcode 순서로 선언해주세요
 * 실제 사용할 떄는 "LinkZipTheme.color.WG10"
 */
val WG10 = Color(0xFFF8F8F8)
val WG20 = Color(0xFFF8F8F8)
val WG30 = Color(0xFFD0D0D0)
val WG40 = Color(0xFFA8A8A8)
val WG50 = Color(0xFF6A6866)
val WG60 = Color(0xFF454342)
val WG70 = Color(0xFF2F2C2A)

val BLACK = Color(0xFF000000)
val WHITE = Color(0xFFFFFFFF)
val RED = Color(0xFFFB4E4E)
val GREEN = Color(0xFF36E390)

val ORANGE_FFAA2C = Color(0xFFFFAA2C)
val ORANGE_FFE6C1 = Color(0xFFFFE6C1)
val ORANGE_FFC737 = Color(0xFFFFC737)
val ORANGE_FFEEB1 = Color(0xFFFFEEB1)

val BROWN_BC783C = Color(0xFFBC783C)
val BROWN_FADECA = Color(0xFFFADECA)

val RED_FB5B63 = Color(0xFFFB5B63)

val GRAY_353E45 = Color(0xFF353E45)
val GRAY_F9D9E2 = Color(0xFFE0E6EB)
val GRAY_E2E6E9 = Color(0xFFE2E6E9)

val BLUE_294459 = Color(0xFF294459)
val BLUE_D6EAF2 = Color(0xFFD6EAF2)
val BLUE_40C3EC = Color(0xFF40C3EC)
val BLUE_C0F0FF = Color(0xFFC0F0FF)
val BLUE_4088F4 = Color(0xFF4088F4)
val BLUE_C8DDFD = Color(0xFFC8DDFD)

val GREEN_2FCE7B = Color(0xFF2FCE7B)
val GREEN_BDF3C2 = Color(0xFFBDF3C2)
val GREEN_719525 = Color(0xFF719525)
val GREEN_F3F4C2 = Color(0xFFF3F4C2)

val PURPLE_8E56FF = Color(0xFF8E56FF)
val PURPLE_EFE7FF = Color(0xFFEFE7FF)

val PINK_FF70CE = Color(0xFFFF70CE)
val PINK_FFE8F7 = Color(0xFFFFE8F7)
val PINK_F9D9E2 = Color(0xFFF9D9E2)

@Stable
class LinkZipColorScheme(
    wg10: Color,
    wg20: Color,
    wg30: Color,
    wg40: Color,
    wg50: Color,
    wg60: Color,
    wg70: Color,
    black: Color,
    white: Color,
    red: Color,
    green: Color,
    orangeFFAA2C: Color,
    orangeFFE6C1: Color,
    orangeFFC737: Color,
    orangeFFEEB1: Color,
    brownBC783C: Color,
    brownFADECA: Color,
    redFB5B63: Color,
    gray353E45: Color,
    grayF9D9E2: Color,
    grayE2E6E9: Color,
    blue294459: Color,
    blueD6EAF2: Color,
    blue40C3EC: Color,
    blueC0F0FF: Color,
    blue4088F4: Color,
    blueC8DDFD: Color,
    green2FCE7B: Color,
    greenBDF3C2: Color,
    green719525: Color,
    greenF3F4C2: Color,
    purple8E56FF: Color,
    purpleEFE7FF: Color,
    pinkFF70CE: Color,
    pinkFFE8F7: Color,
    pinkF9D9E2: Color,
) {
    var wg10 by mutableStateOf(wg10, structuralEqualityPolicy())
        internal set
    var wg20 by mutableStateOf(wg20, structuralEqualityPolicy())
        internal set
    var wg30 by mutableStateOf(wg30, structuralEqualityPolicy())
        internal set
    var wg40 by mutableStateOf(wg40, structuralEqualityPolicy())
        internal set
    var wg50 by mutableStateOf(wg50, structuralEqualityPolicy())
        internal set
    var wg60 by mutableStateOf(wg60, structuralEqualityPolicy())
        internal set
    var wg70 by mutableStateOf(wg70, structuralEqualityPolicy())
        internal set
    var black by mutableStateOf(black, structuralEqualityPolicy())
        internal set
    var white by mutableStateOf(white, structuralEqualityPolicy())
        internal set
    var red by mutableStateOf(red, structuralEqualityPolicy())
        internal set
    var green by mutableStateOf(green, structuralEqualityPolicy())
        internal set
    var orangeFFAA2C by mutableStateOf(orangeFFAA2C, structuralEqualityPolicy())
        internal set
    var orangeFFE6C1 by mutableStateOf(orangeFFE6C1, structuralEqualityPolicy())
        internal set
    var orangeFFC737 by mutableStateOf(orangeFFC737, structuralEqualityPolicy())
        internal set
    var orangeFFEEB1 by mutableStateOf(orangeFFEEB1, structuralEqualityPolicy())
        internal set
    var brownBC783C by mutableStateOf(brownBC783C, structuralEqualityPolicy())
        internal set
    var brownFADECA by mutableStateOf(brownFADECA, structuralEqualityPolicy())
        internal set
    var redFB5B63 by mutableStateOf(redFB5B63, structuralEqualityPolicy())
        internal set
    var gray353E45 by mutableStateOf(gray353E45, structuralEqualityPolicy())
        internal set
    var grayF9D9E2 by mutableStateOf(grayF9D9E2, structuralEqualityPolicy())
        internal set
    var grayE2E6E9 by mutableStateOf(grayE2E6E9, structuralEqualityPolicy())
        internal set
    var blue294459 by mutableStateOf(blue294459, structuralEqualityPolicy())
        internal set
    var blueD6EAF2 by mutableStateOf(blueD6EAF2, structuralEqualityPolicy())
        internal set
    var blue40C3EC by mutableStateOf(blue40C3EC, structuralEqualityPolicy())
        internal set
    var blueC0F0FF by mutableStateOf(blueC0F0FF, structuralEqualityPolicy())
        internal set
    var blue4088F4 by mutableStateOf(blue4088F4, structuralEqualityPolicy())
        internal set
    var blueC8DDFD by mutableStateOf(blueC8DDFD, structuralEqualityPolicy())
        internal set
    var green2FCE7B by mutableStateOf(green2FCE7B, structuralEqualityPolicy())
        internal set
    var greenBDF3C2 by mutableStateOf(greenBDF3C2, structuralEqualityPolicy())
        internal set
    var green719525 by mutableStateOf(green719525, structuralEqualityPolicy())
        internal set
    var greenF3F4C2 by mutableStateOf(greenF3F4C2, structuralEqualityPolicy())
        internal set
    var purple8E56FF by mutableStateOf(purple8E56FF, structuralEqualityPolicy())
        internal set
    var purpleEFE7FF by mutableStateOf(purpleEFE7FF, structuralEqualityPolicy())
        internal set
    var pinkFF70CE by mutableStateOf(pinkFF70CE, structuralEqualityPolicy())
        internal set
    var pinkFFE8F7 by mutableStateOf(pinkFFE8F7, structuralEqualityPolicy())
        internal set
    var pinkF9D9E2 by mutableStateOf(pinkF9D9E2, structuralEqualityPolicy())
        internal set

    fun copy(
        wg10: Color = this.wg10,
        wg20: Color = this.wg20,
        wg30: Color = this.wg30,
        wg40: Color = this.wg40,
        wg50: Color = this.wg50,
        wg60: Color = this.wg60,
        wg70: Color = this.wg70,
        black: Color = this.black,
        white: Color = this.white,
        red: Color = this.red,
        green: Color = this.green,
        orangeFFAA2C: Color = this.orangeFFAA2C,
        orangeFFE6C1: Color = this.orangeFFE6C1,
        orangeFFC737: Color = this.orangeFFC737,
        orangeFFEEB1: Color = this.orangeFFEEB1,
        brownBC783C: Color = this.brownBC783C,
        brownFADECA: Color = this.brownFADECA,
        redFB5B63: Color = this.redFB5B63,
        gray353E45: Color = this.gray353E45,
        grayF9D9E2: Color = this.grayF9D9E2,
        grayE2E6E9: Color = this.grayE2E6E9,
        blue294459: Color = this.blue294459,
        blueD6EAF2: Color = this.blueD6EAF2,
        blue40C3EC: Color = this.blue40C3EC,
        blueC0F0FF: Color = this.blueC0F0FF,
        blue4088F4: Color = this.blue4088F4,
        blueC8DDFD: Color = this.blueC8DDFD,
        green2FCE7B: Color = this.green2FCE7B,
        greenBDF3C2: Color = this.greenBDF3C2,
        green719525: Color = this.green719525,
        greenF3F4C2: Color = this.greenF3F4C2,
        purple8E56FF: Color = this.purple8E56FF,
        purpleEFE7FF: Color = this.purpleEFE7FF,
        pinkFF70CE: Color = this.pinkFF70CE,
        pinkFFE8F7: Color = this.pinkFFE8F7,
        pinkF9D9E2: Color = this.pinkF9D9E2,
    ): LinkZipColorScheme = LinkZipColorScheme(
        wg10,
        wg20,
        wg30,
        wg40,
        wg50,
        wg60,
        wg70,
        black,
        white,
        red,
        green,
        orangeFFAA2C,
        orangeFFE6C1,
        orangeFFC737,
        orangeFFEEB1,
        brownBC783C,
        brownFADECA,
        redFB5B63,
        gray353E45,
        grayF9D9E2,
        grayE2E6E9,
        blue294459,
        blueD6EAF2,
        blue40C3EC,
        blueC0F0FF,
        blue4088F4,
        blueC8DDFD,
        green2FCE7B,
        greenBDF3C2,
        green719525,
        greenF3F4C2,
        purple8E56FF,
        purpleEFE7FF,
        pinkFF70CE,
        pinkFFE8F7,
        pinkF9D9E2
    )
}


/**
 * 지정된 ColorScheme의 내부 값을 다른 값으로 업데이트합니다.
 *
 *
 * 이렇게 하면 LocalTwoTooColor의 값을 사용하는 모든 컴포저블을 재구성하지 않고도
 * ColorScheme의 하위 집합을 효율적으로 업데이트할 수 있습니다.
 *
 *
 * ColorScheme는 LocalColorScheme에 새 값을 제공하면 LocalColoScheme를 사용하는,
 * 모든 컴포저블이 재구성됩니다. 이는 엄청나게 비용이 많이 듭니다.
 *
 *
 * 이 함수는 other의 값과 일치하도록 내부의 상태를 변경합니다.
 * 즉 모든 변경사항은 내부 상태를 변경하고 변경된 특정 값을 읽는 컴포저블만 재구성하도록 합니다.
 */
fun LinkZipColorScheme.updateColorSchemeFrom(other: LinkZipColorScheme) {
    wg10 = other.wg10
    wg20 = other.wg20
    wg30 = other.wg30
    wg40 = other.wg40
    wg50 = other.wg50
    wg60 = other.wg60
    wg70 = other.wg70
    black = other.black
    white = other.white
    red = other.red
    green = other.green
    orangeFFAA2C = other.orangeFFAA2C
    orangeFFE6C1 = other.orangeFFE6C1
    orangeFFC737 = other.orangeFFC737
    orangeFFEEB1 = other.orangeFFEEB1
    brownBC783C = other.brownBC783C
    brownFADECA = other.brownFADECA
    redFB5B63 = other.redFB5B63
    gray353E45 = other.gray353E45
    grayF9D9E2 = other.grayF9D9E2
    grayE2E6E9 = other.grayE2E6E9
    blue294459 = other.blue294459
    blueD6EAF2 = other.blueD6EAF2
    blue40C3EC = other.blue40C3EC
    blueC0F0FF = other.blueC0F0FF
    blue4088F4 = other.blue4088F4
    blueC8DDFD = other.blueC8DDFD
    green2FCE7B = other.green2FCE7B
    greenBDF3C2 = other.greenBDF3C2
    green719525 = other.green719525
    greenF3F4C2 = other.greenF3F4C2
    purple8E56FF = other.purple8E56FF
    purpleEFE7FF = other.purpleEFE7FF
    pinkFF70CE = other.pinkFF70CE
    pinkFFE8F7 = other.pinkFFE8F7
    pinkF9D9E2 = other.pinkF9D9E2
}

enum class ThemeColor {
    Default,
}
fun getCurrentThemeColor(currentTheme: ThemeColor, isDarkTheme: Boolean): LinkZipColorScheme {
    return lightColors()
}

fun lightColors(
    wg10: Color = WG10,
    wg20: Color = WG20,
    wg30: Color = WG30,
    wg40: Color = WG40,
    wg50: Color = WG50,
    wg60: Color = WG60,
    wg70: Color = WG70,
    black: Color = BLACK,
    white: Color = WHITE,
    red: Color = RED,
    green: Color = GREEN,
    orangeFFAA2C: Color = ORANGE_FFAA2C,
    orangeFFE6C1: Color = ORANGE_FFE6C1,
    orangeFFC737: Color = ORANGE_FFC737,
    orangeFFEEB1: Color = ORANGE_FFEEB1,
    brownBC783C: Color = BROWN_BC783C,
    brownFADECA: Color = BROWN_FADECA,
    redFB5B63: Color = RED_FB5B63,
    gray353E45: Color = GRAY_353E45,
    grayF9D9E2: Color = GRAY_F9D9E2,
    grayE2E6E9: Color = GRAY_E2E6E9,
    blue294459: Color = BLUE_294459,
    blueD6EAF2: Color = BLUE_D6EAF2,
    blue40C3EC: Color = BLUE_40C3EC,
    blueC0F0FF: Color = BLUE_C0F0FF,
    blue4088F4: Color = BLUE_4088F4,
    blueC8DDFD: Color = BLUE_C8DDFD,
    green2FCE7B: Color = GREEN_2FCE7B,
    greenBDF3C2: Color = GREEN_BDF3C2,
    green719525: Color = GREEN_719525,
    greenF3F4C2: Color = GREEN_F3F4C2,
    purple8E56FF: Color = PURPLE_8E56FF,
    purpleEFE7FF: Color = PURPLE_EFE7FF,
    pinkFF70CE: Color = PINK_FF70CE,
    pinkFFE8F7: Color = PINK_FFE8F7,
    pinkF9D9E2: Color = PINK_F9D9E2,
): LinkZipColorScheme = LinkZipColorScheme(
    wg10,
    wg20,
    wg30,
    wg40,
    wg50,
    wg60,
    wg70,
    black,
    white,
    red,
    green,
    orangeFFAA2C,
    orangeFFE6C1,
    orangeFFC737,
    orangeFFEEB1,
    brownBC783C,
    brownFADECA,
    redFB5B63,
    gray353E45,
    grayF9D9E2,
    grayE2E6E9,
    blue294459,
    blueD6EAF2,
    blue40C3EC,
    blueC0F0FF,
    blue4088F4,
    blueC8DDFD,
    green2FCE7B,
    greenBDF3C2,
    green719525,
    greenF3F4C2,
    purple8E56FF,
    purpleEFE7FF,
    pinkFF70CE,
    pinkFFE8F7,
    pinkF9D9E2
)

val LocalLinkZipColor = staticCompositionLocalOf {
    lightColors()
}