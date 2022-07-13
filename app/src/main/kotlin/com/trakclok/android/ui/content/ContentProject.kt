package com.trakclok.android.ui.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trakclok.android.R
import com.trakclok.android.utils.extension.toComposeColor
import com.trakclok.android.mapping.params.ParamsContentProject
import com.trakclok.android.mapping.preview.PreviewContentProject
import com.trakclok.android.ui.container.CtIcon
import com.trakclok.android.ui.container.CtSurface

@Preview(widthDp = 400, showBackground = true)
@ExperimentalMaterialApi
@Composable
fun ContentProject(@PreviewParameter(PreviewContentProject::class) params: ParamsContentProject) {
    Column(Modifier.fillMaxWidth()) {
        // --- heading
        Row {
            // --- color
            Surface(
                Modifier
                    .width(6.dp)
                    .height(20.dp),
                color = params.project.color.toComposeColor(),
                content = {},
                shape = RoundedCornerShape(2.dp)
            )

            // --- project name
            Text(
                text = params.project.name,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontSize = 18.sp
            )

            // --- pause button
            CtSurface(
                Modifier
                    .size(36.dp),
                click = { /*TODO*/ },
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                ripple = MaterialTheme.colorScheme.tertiary
            ) {
                CtIcon(
                    res = R.drawable.vd_pause,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // --- time
        Row(Modifier.padding(start = 24.dp, top = 8.dp)) {

            // --- hours
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = params.time.value.hours,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "h",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            }

            // --- minutes
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = params.time.value.minutes,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "m",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            }

            // --- seconds
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = params.time.value.seconds,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "s",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            }
        }

        // --- days / months / years
        Row(Modifier.padding(start = 28.dp)) {

            // --- years
            if (params.time.value.years != "0")
                Text(
                    text = params.time.value.years + " years, ",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

            // --- months
            if (params.time.value.months != "0")
                Text(
                    text = params.time.value.months + " months, ",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

            // --- days
            if (params.time.value.days != "0")
                Text(
                    text = params.time.value.days + " days",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
        }

    }
}