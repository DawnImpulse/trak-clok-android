package com.trakclok.android.ui.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.trakclok.android.R
import com.trakclok.android.mapping.params.ParamsContentHomeHeader
import com.trakclok.android.mapping.preview.PreviewContentHomeHeader
import com.trakclok.android.ui.container.CtBoxIcon
import com.trakclok.android.utils.extension.showAnim
import kotlinx.coroutines.launch

@Preview(widthDp = 400)
@ExperimentalMaterialApi
@Composable
fun ContentHomeHeader(@PreviewParameter(PreviewContentHomeHeader::class) params: ParamsContentHomeHeader) {

    // --- scope
    val scope = rememberCoroutineScope()

    Box(
        Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        // --- date
        Column {
            Text(
                text = params.date,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${params.month}, ${params.day}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // --- actions
        Row(Modifier.align(Alignment.BottomEnd)) {
            CtBoxIcon(
                res = R.drawable.vd_user,
                click = {

                },
                tint = MaterialTheme.colorScheme.secondary,
                size = 48.dp
            )

            CtBoxIcon(
                res = R.drawable.vd_plus_square,
                click = {
                    scope.launch {
                        params.viewModel.projectCreated.value = false
                        params.viewModel.startCurrentTime()
                        params.viewModel.sheetState.showAnim()
                    }
                },
                tint = MaterialTheme.colorScheme.primary,
                size = 48.dp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}