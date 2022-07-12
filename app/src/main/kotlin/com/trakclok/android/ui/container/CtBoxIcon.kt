package com.trakclok.android.ui.container

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CtBoxIcon(
    @DrawableRes res: Int,
    click: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    padding: Dp = 0.dp,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    ripple: Color = MaterialTheme.colorScheme.tertiary,
    bounded: Boolean = false,
) {
    CtBox(click = click, modifier = modifier.size(size), ripple = ripple, bounded = bounded) {
        CtIcon(
            res = res, modifier = Modifier
                .padding(10.dp + padding)
                .fillMaxSize()
                .align(Alignment.Center),
            tint = tint
        )
    }
}