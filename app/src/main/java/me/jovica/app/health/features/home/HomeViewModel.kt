package me.jovica.app.health.features.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.jovica.app.health.data.HealthConnectManager
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(val healthConnectManager: HealthConnectManager) :
    ViewModel() {


    private val _list = MutableStateFlow<List<SleepSessionRecord>>(listOf())
    val list = _list

    suspend fun getSleepData(start: Instant, end: Instant) {
        _list.value = healthConnectManager.readSleepSessions(start, end).toMutableStateList()

    }
}