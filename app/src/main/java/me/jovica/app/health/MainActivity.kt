package me.jovica.app.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.jovica.app.health.data.HealthConnectManager
import javax.inject.Inject


enum class AppState {
    LOADING,
    READY
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var healthConnectManager: HealthConnectManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)


        val sdk = healthConnectManager.availability.value
        var hasPermission by mutableStateOf(false)
        var ready by mutableStateOf(false)

        lifecycleScope.launch {
            hasPermission = healthConnectManager.hasAllPermissions(HealthConnectManager.permissionsSet)
            ready = true
        }


        splashScreen.setKeepOnScreenCondition {!ready}
        enableEdgeToEdge()
        setContent {
            JsHealthApp(sdk, hasPermission) {
                lifecycleScope.launch {
                    hasPermission =
                        healthConnectManager.hasAllPermissions(HealthConnectManager.permissionsSet)

                }
            }
        }
    }
}

