package me.jovica.app.health.features.welcome

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.jovica.app.health.data.HealthConnectManager

@ExperimentalMaterial3Api
@Composable
fun WelcomeScreen(settingsViewModel: WelcomeViewModel, goNext: () -> Unit) {
    val username by settingsViewModel.username.collectAsState()
    val permissions by settingsViewModel.hasPermissions.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        settingsViewModel.updateHasPermissions()
    }

    val permissionsLauncher =
        rememberLauncherForActivityResult(settingsViewModel.healthConnectManager.requestPermissionsActivityContract()) { grantedPermissions: Set<String> ->
            scope.launch {
                settingsViewModel.updateHasPermissions()

            }
        }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Welcome")
        })
    }) { padding ->
        Column(Modifier.padding(padding)) {
            Row(Modifier.padding(16.dp, 0.dp)) {
                Text("Enter Your name:")
            }
            Row(Modifier.padding(16.dp, 8.dp)) {
                OutlinedTextField(label = { Text("Name") },
                    value = username,
                    onValueChange = { v -> settingsViewModel.updateUsername(v) })
            }
            if (!permissions) {
                Row(Modifier.padding(16.dp, 8.dp)) {
                    Button({ permissionsLauncher.launch(HealthConnectManager.permissionsSet) }) {
                        Text("Enable Heath Connect")
                    }
                }
            } else {
                Row(Modifier.padding(16.dp, 8.dp)) {
                    Text(text = "Health Connect Enabled")
                }

                Row(Modifier.padding(16.dp, 8.dp)) {
                    Button(onClick = {
                        settingsViewModel.finishSetup()
                        goNext()
                    }) {
                        Text(text = "Finish Setup")
                    }

                }
            }
        }
    }


}