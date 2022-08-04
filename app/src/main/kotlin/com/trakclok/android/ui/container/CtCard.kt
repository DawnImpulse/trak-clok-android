package com.trakclok.android.ui.container

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun CtCard(
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    elevation: CardElevation = CardDefaults.cardElevation(),
    colors: CardColors = CardDefaults.cardColors(),
    ripple: Color = Color.Black,
    shape: Shape = RoundedCornerShape(0),
    bounded: Boolean = false, // ripple bounded or circular (unbounded)
    click: () -> Unit,
    boxWidth: Dp,
    boxHeight: Dp = boxWidth,
    cardWidth: Dp = boxWidth,
    cardHeight: Dp = cardWidth,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .width(boxWidth)
            .height(boxHeight)
    ) {
        Card(
            modifier
                .clickable(
                    onClick = click,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = bounded, color = ripple)
                )
                .align(Alignment.Center)
                .width(24.dp)
                .height(24.dp),
            border = border,
            elevation = elevation,
            colors = colors,
            content = content,
            shape = shape
        )
    }
}