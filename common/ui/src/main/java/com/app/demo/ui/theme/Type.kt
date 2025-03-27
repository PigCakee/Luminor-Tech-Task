package com.app.demo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.demo.ui.R

val font = FontFamily(
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_extra_light, FontWeight.ExtraLight),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_semi_bold, FontWeight.SemiBold),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_extra_bold, FontWeight.ExtraBold),
    Font(R.font.roboto_black, FontWeight.Black),
)

// Set of Material typography styles to start with
val Typography = Typography()

@Stable
data class DemoTypography(
    val H1XL: TextStyle,
    val H2XL: TextStyle,
    val H3XL: TextStyle,
    val H4XL: TextStyle,
    val H5XL: TextStyle,
    val H6XL: TextStyle,
    val P1XL: TextStyle,
    val P2XL: TextStyle,
    val P3XL: TextStyle,
    val P4XL: TextStyle,
    val P5XL: TextStyle,
    val H1MD: TextStyle,
    val H2MD: TextStyle,
    val H3MD: TextStyle,
    val H4MD: TextStyle,
    val H5MD: TextStyle,
    val H6MD: TextStyle,
    val P1MD: TextStyle,
    val P2MD: TextStyle,
    val P3MD: TextStyle,
    val P4MD: TextStyle,
    val P5MD: TextStyle
)

@Composable
fun ProvideDemoTypography(
    typography: DemoTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDemoTypography provides typography, content = content)
}


private val DefaultDemoTypography = DemoTypography(
    H1XL = TextStyle(
        fontSize = 26.sp,
        lineHeight = 32.sp,
        fontFamily = font,
        fontWeight = FontWeight.SemiBold
    ),
    H2XL = TextStyle(
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H3XL = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H4XL = TextStyle(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H5XL = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H6XL = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    P1XL = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    ),
    P2XL = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = font,
        fontWeight = FontWeight.SemiBold
    ),
    P3XL = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    P4XL = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    ),
    P5XL = TextStyle(
        fontSize = 10.sp,
        lineHeight = 12.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H1MD = TextStyle(
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.SemiBold
    ),
    H2MD = TextStyle(
        fontSize = 22.sp,
        lineHeight = 24.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H3MD = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H4MD = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H5MD = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    H6MD = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    P1MD = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    ),
    P2MD = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = font,
        fontWeight = FontWeight.SemiBold
    ),
    P3MD = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    ),
    P4MD = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    ),
    P5MD = TextStyle(
        fontSize = 10.sp,
        lineHeight = 12.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    )
)

val LocalDemoTypography = staticCompositionLocalOf { DefaultDemoTypography }
