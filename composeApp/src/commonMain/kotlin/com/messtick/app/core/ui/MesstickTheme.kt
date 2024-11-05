package com.messtick.app.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.messtick.app.core.ui.theme.MesstickCupertinoTypography
import com.messtick.app.core.ui.theme.MesstickTypography
import com.messtick.app.core.ui.theme.darkSchemeAdaptive
import com.messtick.app.core.ui.theme.darkSchemeMaterial
import com.messtick.app.core.ui.theme.lightSchemeAdaptive
import com.messtick.app.core.ui.theme.lightSchemeMaterial
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.CupertinoThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.MaterialThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.Theme

expect fun determineTheme(): Theme


@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun MesstickTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    theme: Theme = determineTheme(),
    content: @Composable () -> Unit
) {
    AdaptiveTheme(
        target = theme,
        material = MaterialThemeSpec.Default(
            colorScheme = if (useDarkTheme) {
                darkSchemeMaterial
            } else {
                lightSchemeMaterial
            },
            typography = MesstickTypography()
        ),
        cupertino = CupertinoThemeSpec.Default(
            colorScheme = if (useDarkTheme) {
                darkSchemeAdaptive
            } else {
                lightSchemeAdaptive
            },
            typography = MesstickCupertinoTypography()
        ),
        content = content
    )
}