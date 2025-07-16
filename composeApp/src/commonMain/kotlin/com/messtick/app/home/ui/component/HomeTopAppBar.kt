@file:OptIn(ExperimentalMaterial3Api::class)

package com.messtick.app.home.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.messtick.app.core.ui.MesstickTheme
import com.messtick.app.core.ui.theme.primaryLight
import messtick.composeapp.generated.resources.Res
import messtick.composeapp.generated.resources.chats
import messtick.composeapp.generated.resources.messtick
import messtick.composeapp.generated.resources.messtick_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollFraction = topAppBarScrollBehavior.state.collapsedFraction

    val expandedColor = Color.White
    val collapsedColor = MaterialTheme.colorScheme.onSurface
    val tintColor = primaryLight

    val contentColor by animateColorAsState(
        targetValue = Color(
            red = lerp(expandedColor.red, collapsedColor.red, scrollFraction),
            green = lerp(expandedColor.green, collapsedColor.green, scrollFraction),
            blue = lerp(expandedColor.blue, collapsedColor.blue, scrollFraction),
            alpha = lerp(expandedColor.alpha, collapsedColor.alpha, scrollFraction)
        )
    )
    val imageContentColor by animateColorAsState(
        targetValue = Color(
            red = lerp(expandedColor.red, tintColor.red, scrollFraction),
            green = lerp(expandedColor.green, tintColor.green, scrollFraction),
            blue = lerp(expandedColor.blue, tintColor.blue, scrollFraction),
            alpha = lerp(expandedColor.alpha, tintColor.alpha, scrollFraction)
        )
    )
    MediumTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary ,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = imageContentColor,
            titleContentColor = contentColor,
            actionIconContentColor =contentColor
        ),
        title = {
            Column {
                Text(
                    text = stringResource(Res.string.chats),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp).size(40.dp),
                painter = painterResource(Res.drawable.messtick_icon),
                contentDescription = null,
                tint = imageContentColor
            )
        },
        actions = {
            IconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = onLogout
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = topAppBarScrollBehavior
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeTopAppBarPreview() {
    MesstickTheme {
        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        HomeTopAppBar(
            topAppBarScrollBehavior = scrollBehavior,
            onLogout = {}
        )
    }
}