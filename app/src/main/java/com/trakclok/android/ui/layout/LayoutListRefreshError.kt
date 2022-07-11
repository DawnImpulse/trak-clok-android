package com.trakclok.android.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.statusBarsPadding
import com.trakclok.android.R

@Composable
fun LayoutListRefreshError(error: String , callback: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 24.dp)
            .statusBarsPadding(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // lottie
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_astro))
            LottieAnimation(
                composition,
                iterations = LottieConstants.IterateForever,
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )

            // error message
            Text(
                text = error,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )

            // reload button
            Button(
                onClick = { callback.invoke() },
                modifier = Modifier
                    .padding(4.dp)
                    .width(200.dp)
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(
                    text = "RELOAD",
                    color = MaterialTheme.colorScheme.onError,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 4.dp,
                        end = 4.dp,
                        bottom = 2.dp
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}