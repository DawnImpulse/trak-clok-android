package com.trakclok.android.ui.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.trakclok.android.R

@Preview(heightDp = 100, widthDp = 200)
@Composable
fun ItemLottieReload(height: Int = 100) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(height.dp)
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                ItemRaw(
                    light = R.raw.loading_line,
                    dark = R.raw.loading_line_night
                )
            )
        )
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )
    }
}