package com.app.demo.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.demo.ui.component.Loader
import com.app.demo.ui.theme.DemoTheme

@Composable
fun ScreenLoader(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Loader(
            modifier = Modifier.align(Alignment.Center),
            tint = DemoTheme.colors.primary
        )
    }
}