package me.jovica.app.health

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.lifecycle.viewmodel.compose.viewModel
import me.jovica.app.health.features.welcome.WelcomeScreen
import me.jovica.app.health.ui.nav.MainNav
import me.jovica.app.health.ui.theme.JsHealthTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JsHealthApp(sdkGood: Int, hasPermission: Boolean, updateAppState :() -> Unit ) {


    val context = LocalContext.current

    JsHealthTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {

            when (sdkGood) {
                HealthConnectClient.SDK_AVAILABLE -> {

                    if (hasPermission) {
                        MainNav()
                    } else {
                        WelcomeScreen(viewModel()) {
                            updateAppState()
                        }
                    }
                }

                HealthConnectClient.SDK_UNAVAILABLE -> Scaffold { padding ->
                    Column(Modifier.padding(padding)) {
                        Text("Health Connect is not available on your deviceW")
                    }
                }

                HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> Scaffold { padding ->
                    Column(Modifier.padding(padding)) {
                        Text("Install or update Health Connect from the Google Play store")
                        Button(onClick = {
                            val uriString =
                                "market://details?url=healthconnect%3A%2F%2Fonboarding"
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
