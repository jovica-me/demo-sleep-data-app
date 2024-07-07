package me.jovica.app.health.features.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.jovica.app.health.data.HealthConnectManager
import me.jovica.app.health.data.SettingsRepository
import javax.inject.Inject


data class SetupState(val name: String, val finished: Boolean)

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    val healthConnectManager: HealthConnectManager
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished

    private val _hasPermissions = MutableStateFlow(false)
    val hasPermissions = _hasPermissions


    init {
        viewModelScope.launch {
            settingsRepository.currentName.collect { name ->
                _username.value = name
            }
            settingsRepository.isFinished.collect { finished ->
                _isFinished.value = finished
            }
        }
    }
    suspend fun updateHasPermissions() {
        viewModelScope.launch {
            _isFinished.value = settingsRepository.isFinished.first()
            _hasPermissions.value = healthConnectManager.hasAllPermissions(HealthConnectManager.permissionsSet)
        }
    }
    fun updateUsername(input: String) {
        _username.value = input
        viewModelScope.launch {
            settingsRepository.setName(input)
        }
    }

    fun finishSetup() {
        _isFinished.value = true
        viewModelScope.launch {
            settingsRepository.finishSetup()
        }
    }
}