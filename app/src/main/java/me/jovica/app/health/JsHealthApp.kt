package me.jovica.app.health

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.navigation.compose.rememberNavController
import me.jovica.app.health.ui.nav.MainNav
import me.jovica.app.health.ui.theme.JsHealthTheme


val permissions = setOf(
    HealthPermission.getReadPermission(ExerciseSessionRecord::class),
)


@Composable
fun JsHealthApp(healthConnectManager: HealthConnectManager) {

    val sdkGood = healthConnectManager.availability.value


    val permissionsLauncher =
        rememberLauncherForActivityResult(healthConnectManager.requestPermissionsActivityContract()) {}

    val context = LocalContext.current

    JsHealthTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {

            when (sdkGood) {
                HealthConnectClient.SDK_AVAILABLE -> MainNav()

                HealthConnectClient.SDK_UNAVAILABLE -> Text("Health Connect is not available on your device ")

                HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                    Column {
                        Text("Install or update Health Connect from the Google Play store")
                        Button(onClick = {
                            val uriString = "market://details?url=healthconnect%3A%2F%2Fonboarding"
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW).apply {
                                    setPackage("com.android.vending")
                                    data = Uri.parse(uriString)
                                    putExtra("overlay", true)
                                    putExtra("callerId", context.packageName)
                                }
                            )
                        }) {
                            Text("Install")
                        }
                    }
                }
            }

        }
    }

}


//    JsHealthTheme {
//        Scaffold { padd ->
//            Column(Modifier.padding(padd)) {
//                if(sdkGood == HealthConnectClient.SDK_AVAILABLE) {
//                    Text("We good")
//                    Button(onClick = {
//                        permissionsLauncher.launch(permissions)
//                    }) {
//                        Text("Grant Permissions")
//                    }
//                } else {
//                    Text("Noooo")
//                }
//
//            }
//        }
//    }
