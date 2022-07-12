package com.trakclok.android.ui.container

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CtSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    click: () -> Unit,
    bounded: Boolean = true,
    ripple: Color  = Color.Black,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.clickable(
            onClick = click,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = bounded, color = ripple)
        ),
        color = color,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        shape = shape,
        content = content
    )
}