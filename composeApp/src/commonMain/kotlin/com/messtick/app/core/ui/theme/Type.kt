package com.messtick.app.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.prompt_medium
import messtick.composeapp.generated.resources.prompt_regular
import org.jetbrains.compose.resources.Font

@Composable
fun BodyFontFamily() = FontFamily(
    Font(
        resource = Res.font.prompt_regular,
    )
)

@Composable
fun DisplayFontFamily()= FontFamily(
    Font(
        resource = Res.font.prompt_medium,
        weight = FontWeight.Medium
    )
)

// Default Material 3 typography values
val baseline = Typography()

@Composable
fun MesstickTypography() = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = DisplayFontFamily()),
    displayMedium = baseline.displayMedium.copy(fontFamily = DisplayFontFamily()),
    displaySmall = baseline.displaySmall.copy(fontFamily = DisplayFontFamily()),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = DisplayFontFamily()),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = DisplayFontFamily()),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = DisplayFontFamily()),
    titleLarge = baseline.titleLarge.copy(fontFamily = DisplayFontFamily()),
    titleMedium = baseline.titleMedium.copy(fontFamily = DisplayFontFamily()),
    titleSmall = baseline.titleSmall.copy(fontFamily = DisplayFontFamily()),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = BodyFontFamily()),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = BodyFontFamily()),
    bodySmall = baseline.bodySmall.copy(fontFamily = BodyFontFamily()),
    labelLarge = baseline.labelLarge.copy(fontFamily = BodyFontFamily()),
    labelMedium = baseline.labelMedium.copy(fontFamily = BodyFontFamily()),
    labelSmall = baseline.labelSmall.copy(fontFamily = BodyFontFamily()),
)