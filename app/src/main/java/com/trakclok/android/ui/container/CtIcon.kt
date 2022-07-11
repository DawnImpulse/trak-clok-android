package com.trakclok.android.ui.container

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun CtIcon(
    modifier: Modifier = Modifier,
    @DrawableRes res: Int,
    tint: Color = MaterialTheme.colors.onBackground,
) {
    Icon(
        painter = painterResource(id = res),
        contentDescription = null,
        modifier = modifier,
        tint = tint,
    )
}