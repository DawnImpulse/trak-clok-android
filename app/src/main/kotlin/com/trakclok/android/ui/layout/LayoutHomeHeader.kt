package com.trakclok.android.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trakclok.android.R
import com.trakclok.android.ui.container.CtBox
import com.trakclok.android.ui.container.CtBoxIcon

@Preview(widthDp = 400)
@Composable
fun LayoutHomeHeader(date: String = "09", month: String = "April, Sunday") {
    Box(Modifier.padding(horizontal = 24.dp)) {
        // --- date
        Column {
            Text(
                text = date,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = month,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // --- actions
        Row(Modifier.align(Alignment.BottomEnd)) {
            CtBoxIcon(
                res = R.drawable.vd_user,
                click = { /*TODO*/ },
                tint = MaterialTheme.colorScheme.secondary,
                size = 48.dp
            )

            CtBoxIcon(
                res = R.drawable.vd_plus_square,
                click = { /*TODO*/ },
                tint = MaterialTheme.colorScheme.primary,
                size = 48.dp
            )
        }
    }
}