package com.trakclok.android.ui.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.trakclok.android.R
import com.trakclok.android.ui.item.ItemRaw

@Composable
fun CtLottie(height: Int = 200, itemRaw: Int) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(height.dp)
    ) {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(itemRaw)
        )
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )
    }
}