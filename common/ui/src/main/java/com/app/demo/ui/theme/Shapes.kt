package com.app.demo.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf

@Stable
data class DemoShapes(
    val xxs: RoundedCornerShape,
    val xs: RoundedCornerShape,
    val s: RoundedCornerShape,
    val m: RoundedCornerShape,
    val l: RoundedCornerShape,
    val xl: RoundedCornerShape,
    val xxl: RoundedCornerShape
)

private val DefaultDemoShapes = DemoShapes(
    xxs = RadiusXXS,
    xs = RadiusXS,
    s = RadiusS,
    m = RadiusM,
    l = RadiusL,
    xl = RadiusXL,
    xxl = RadiusXXL
)

val LocalDemoShapes = staticCompositionLocalOf { DefaultDemoShapes }

@Composable
fun ProvideDemoShapes(
    shapes: DemoShapes,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDemoShapes provides shapes, content = content)
}