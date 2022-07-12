package com.trakclok.android.ui.container

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CtBox(
    modifier: Modifier = Modifier,
    ripple: Color = Color.Black,
    bounded: Boolean = false, // ripple bounded or circular (unbounded)
    click: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier =
        modifier
            .clickable(
                onClick = click,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = bounded, color = ripple)
            ),
        content = content
    )
}