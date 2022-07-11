package com.trakclok.android.ui.item

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.trakclok.android.R
import com.trakclok.android.utils.Cfg

@Composable
fun ItemFabBack() {
    FloatingActionButton(
        modifier = Modifier
            .padding(end = 16.dp, bottom = 8.dp)
            .navigationBarsPadding(bottom = true)
            .size(52.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 45)),
        onClick = { Cfg.navigation.popBackStack() }) {

        // icon in fab
        Icon(
            painter = painterResource(id = R.drawable.vd_keyboard_backspace),
            contentDescription = null,
            modifier = Modifier.size(26.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}