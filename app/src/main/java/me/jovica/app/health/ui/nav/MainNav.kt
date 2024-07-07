package me.jovica.app.health.ui.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import me.jovica.app.health.ui.theme.JsHealthTheme

@Composable
fun MainNav() {
    val navController = rememberNavController()


    NavHost(navController, startDestination = HomeScreen) {
        composable<HomeScreen> {

            Scaffold { padding ->
                Column(Modifier.padding(padding)) {
                    Text("Home")
                    Button(onClick = {
                        navController.navigate(SettingsScreen)
                    }) {
                        Text("Settings")
                    }
                    Button(onClick = {
                        navController.navigate(OneNightData(2))
                    }) {
                        Text("Night 2")
                    }
                }
            }

        }

        composable<SettingsScreen> {

            Scaffold { padding ->
                Column(Modifier.padding(padding)) {
                    Text("Settings")
                }
            }

        }

        composable<OneNightData> {
            val args = it.toRoute<OneNightData>()

            Scaffold { padding ->
                Column(Modifier.padding(padding)) {
                    Text("Night ${args.nightId}")
                }

            }

        }
    }

}

@Serializable
object HomeScreen

@Serializable
object SettingsScreen

@Serializable
data class OneNightData(
    val nightId: Int
)



