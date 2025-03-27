package com.app.demo.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.demo.ui.R
import com.app.demo.ui.theme.DemoTheme
import com.app.demo.ui.theme.Neutral00
import com.app.demo.ui.theme.Primary60

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    tint: Color = Neutral00
) {
    val transition = rememberInfiniteTransition()
    val spinAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_loader_x20),
        contentDescription = "Loading",
        modifier = modifier.rotate(spinAngle),
        tint = tint
    )
}

@Preview
@Composable
private fun LoaderPreview() {
    DemoTheme {
        Loader(
            modifier = Modifier.size(40.dp),
            tint = Primary60
        )
    }
}
