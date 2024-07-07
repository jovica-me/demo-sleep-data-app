package me.jovica.app.health.features.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.jovica.app.health.data.HealthConnectManager
import me.jovica.app.health.data.SettingsRepository
import javax.inject.Inject


data class SetupState(val name: String, val finished: Boolean)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    val healthConnectManager: HealthConnectManager
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _hasPermissions = MutableStateFlow(false)
    val hasPermissions = _hasPermissions


    init {
        viewModelScope.launch {
            settingsRepository.currentName.collect { name ->
                _username.value = name
            }

        }
    }
    suspend fun updateHasPermissions() {
        viewModelScope.launch {

            _hasPermissions.value = healthConnectManager.hasAllPermissions(HealthConnectManager.permissionsSet)
        }
    }
    fun updateUsername(input: String) {
        _username.value = input
        viewModelScope.launch {
            settingsRepository.setName(input)
        }
    }


}