package com.messtick.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.messtick.app.core.di.messtickModules
import com.mmk.kmpnotifier.permission.permissionUtil
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.dsl.koinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionUtil by permissionUtil()
        permissionUtil.askNotificationPermission()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
        )
        setContent {
            KoinApplication(
                application = {
                    androidContext(applicationContext)
                    modules(messtickModules)
                }
            ) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}