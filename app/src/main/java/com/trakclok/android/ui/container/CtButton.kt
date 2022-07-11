package com.trakclok.android.ui.container

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CtButton(
    onClick: () -> Unit,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    enabled: Boolean = true
) {
    // background
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = MaterialTheme.colorScheme.contentColorFor(color)
        ),
        shape = shape,
        enabled = enabled
    ) {
        Text(
            text = label,
            style = labelStyle,
            color = MaterialTheme.colorScheme.contentColorFor(color)
        )
    }
}