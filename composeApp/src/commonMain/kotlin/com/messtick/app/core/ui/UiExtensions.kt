package com.messtick.app.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.util.lerp


@Composable
fun lerpDp(start: Dp, end: Dp, fraction: Float): Dp {
    return Dp(lerp(start.value, end.value, fraction))
}