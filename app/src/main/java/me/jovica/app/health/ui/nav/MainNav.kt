package me.jovica.app.health.ui.nav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import me.jovica.app.health.features.home.HomeScreen
import me.jovica.app.health.features.home.HomeViewModel
import me.jovica.app.health.features.setup.SettingsScreen
import me.jovica.app.health.features.setup.SettingsViewModel


@ExperimentalMaterial3Api
@Composable
fun MainNav() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeScreen) {
        composable<HomeScreen> {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                { navController.navigate(SettingsScreen) },
                { oneNightData: OneNightDataScreeen -> navController.navigate(oneNightData) },
                viewModel
            )
        }

        composable<SettingsScreen> {
            val viewModel = hiltViewModel<SettingsViewModel>()
            SettingsScreen(viewModel)
        }

        composable<OneNightDataScreeen> {
            val args = it.toRoute<OneNightDataScreeen>()

            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Night ${args.nightId}") })
                }
            ) { padding ->
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
data class OneNightDataScreeen(
    val nightId: Int
)



