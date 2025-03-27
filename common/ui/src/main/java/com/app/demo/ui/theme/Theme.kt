package com.app.demo.ui.theme

import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.RippleDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

private val LightColorScheme = lightColorScheme()

@Composable
fun DemoTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    ProvideDemoColors(DemoTheme.colors) {
        ProvideDemoTypography(DemoTheme.typography) {
            ProvideDemoShapes(DemoTheme.shapes) {
                MaterialTheme(
                    colorScheme = colorScheme,
                    typography = Typography,
                    shapes = Shapes(
                        extraSmall = DemoTheme.shapes.xs,
                        small = DemoTheme.shapes.s,
                        medium = DemoTheme.shapes.m,
                        large = DemoTheme.shapes.l,
                        extraLarge = DemoTheme.shapes.xl,
                    )
                ) {
                    CompositionLocalProvider(
                        LocalRippleConfiguration provides RippleConfiguration(
                            color = DemoTheme.colors.onSurface,
                            rippleAlpha = RippleDefaults.RippleAlpha
                        ),
                        content = content
                    )
                }
            }
        }
    }
}

object DemoTheme {
    val colors: DemoColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDemoColors.current

    val typography: DemoTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDemoTypography.current

    val shapes: DemoShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalDemoShapes.current
}

@Stable
data class DemoColors(
    val brand: Color,
    val brandSecondary: Color,
    val brandSecondaryAccent: Color,
    val buttonPrimary: Color,
    val buttonSecondary: Color,
    val buttonDisabled: Color,
    val onBackground: Color,
    val uiBackground: Color,
    val uiBorder: Color,
    val surface: Color,
    val onSurface: Color,
    val primary: Color,
    val onPrimary: Color,
    val primaryBackground: Color,
    val primaryBackgroundVariant: Color,
    val secondary: Color,
    val onSecondary: Color,
    val windowBackground: Color,
    val statusBar: Color,
    val navBar: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textSecondarySubHeader: Color,
    val textDisabled: Color,
    val textHint: Color,
    val textHighlight: Color,
    val textFieldBorder: Color,
    val textFieldBorderFocused: Color,
    val textFieldBackgroundDisabled: Color,
    val searchTextFieldBackground: Color,
    val textFieldIcon: Color,
    val topBar: Color,
    val linkIcon: Color,
    val error: Color,
    val errorBorder: Color,
    val errorBackground: Color,
    val success: Color,
    val successVariant2: Color,
    val successBackground: Color,
    val warning: Color,
    val isDark: Boolean
)

@Composable
fun ProvideDemoColors(
    colors: DemoColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDemoColors provides colors, content = content)
}


private val LocalDemoColors = staticCompositionLocalOf {
    DemoColors(
        brand = Primary80,
        brandSecondary = Primary40,
        brandSecondaryAccent = Secondary,
        buttonPrimary = Black,
        buttonSecondary = Secondary,
        buttonDisabled = Neutral20,
        onBackground = Neutral05,
        uiBackground = White,
        uiBorder = Neutral10,
        surface = White,
        onSurface = Neutral90,
        primary = Primary60,
        onPrimary = White,
        primaryBackground = Primary10,
        primaryBackgroundVariant = Primary20,
        secondary = Primary80,
        onSecondary = White,
        windowBackground = White,
        statusBar = White,
        navBar = White,
        textPrimary = Neutral90,
        textSecondary = Neutral50,
        textSecondarySubHeader = Neutral40,
        textDisabled = Neutral30,
        textHint = Neutral40,
        textHighlight = Primary60,
        textFieldBorder = Neutral20,
        textFieldBorderFocused = Primary60,
        textFieldBackgroundDisabled = Neutral05,
        searchTextFieldBackground = Neutral05,
        textFieldIcon = Neutral30,
        topBar = Primary10,
        linkIcon = Primary40,
        error = Error60,
        errorBorder = Error40,
        errorBackground = Error10,
        success = Success50,
        successVariant2 = Success60,
        successBackground = Success10,
        warning = Warning60,
        isDark = false
    )
}
