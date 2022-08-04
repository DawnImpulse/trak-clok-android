package com.trakclok.android.ui.view

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.trakclok.android.R
import com.trakclok.android.helpers.HelperAuth
import com.trakclok.android.ui.container.CtButton
import com.trakclok.android.ui.other.dialog.DialogLoading
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.Route
import com.trakclok.android.viewmodel.ViewModelAuth

@ExperimentalMaterial3Api
@Preview(widthDp = 400, showBackground = true, showSystemUi = true)
@Composable
fun ViewAuth(viewModelAuth: ViewModelAuth = viewModel()) {

    // --- utils
    val scope = rememberCoroutineScope()
    val context: Context = LocalContext.current
    val result =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            viewModelAuth.finishGoogleLogin(context as Activity, it)
        }

    // --- show loading dialog
    AnimatedVisibility(visible = viewModelAuth.loading.value) {
        DialogLoading()
    }

    // --- layout
    Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        Column(
            Modifier
                .padding(vertical = 48.dp, horizontal = 24.dp)
        ) {

            // --- lottie
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.hello)
                )
                LottieAnimation(
                    composition,
                    iterations = LottieConstants.IterateForever,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(Modifier.weight(1f))

            // --- welcome
            Text(
                text = "Welcome to TrakClok",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            // --- login info
            Text(
                text = "Since your data is kept confidential, hence we require you to login first before using the app :)",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 12.dp),
                letterSpacing = 0.5.sp
            )

            // --- google button
            Box(Modifier.padding(top = 64.dp)) {
                CtButton(
                    onClick = { viewModelAuth.startGoogleLogin(context, result) },
                    label = "GOOGLE",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp),
                    shape = RoundedCornerShape(20.dp),
                    labelStyle = MaterialTheme.typography.titleSmall,
                    labelSpacing = 1.5.sp
                )
            }

            // --- tnc / privacy
            Box(
                Modifier
                    .padding(top = 64.dp, bottom = 36.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "T&C",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(end = 8.dp),
                        letterSpacing = 0.5.sp
                    )

                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )

                    Text(
                        text = "Privacy Policy",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(start = 8.dp),
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}