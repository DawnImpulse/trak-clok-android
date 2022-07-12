package com.trakclok.android.ui.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsPadding

@ExperimentalMaterial3Api
@Composable
fun LayoutListReloadError(error: String, callback: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // message
            Text(
                text = error,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            // --- button
            Button(
                onClick = { callback?.invoke() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Retry",
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        top = 4.dp,
                        end = 12.dp,
                        bottom = 4.dp
                    )
                )
            }
        }
    }
}