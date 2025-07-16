package com.messtick.app.core.ui.util

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

val enterSlideInAnimation =  slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(durationMillis = 300)
) + fadeIn(
    animationSpec = tween(durationMillis = 300)
)

val exitSlideOutAnimation = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(durationMillis = 300)
) + fadeOut(
    animationSpec = tween(durationMillis = 300)
)

val popEnterSlideInAnimation = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(durationMillis = 300)
) + fadeIn(
    animationSpec = tween(durationMillis = 300)
)

val popExitSlideOutAnimation = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(durationMillis = 300)
) + fadeOut(
    animationSpec = tween(durationMillis = 300)
)
