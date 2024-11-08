package com.messtick.app.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.messtick.app.core.ui.theme.MesstickTypography
import com.messtick.app.core.ui.theme.darkSchemeMaterial
import com.messtick.app.core.ui.theme.lightSchemeMaterial

@Composable
fun MesstickTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) darkSchemeMaterial else lightSchemeMaterial,
        typography = MesstickTypography(),
        content = content
    )
}