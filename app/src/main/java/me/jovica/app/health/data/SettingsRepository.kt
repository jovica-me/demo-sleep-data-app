package me.jovica.app.health.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "setting"
)

class SettingsRepository(
    private val context: Context
) {

    private companion object {
        val NAME = stringPreferencesKey("name")
        val FINISHED_SETUP = booleanPreferencesKey("setup_done")
    }


    val currentName = context.dataStore.data.map {
        it[NAME] ?: "_"
    }
    val isFinished = context.dataStore.data.map {
        it[FINISHED_SETUP] ?: false
    }

    suspend fun setName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }

    suspend fun finishSetup() {
        context.dataStore.edit { preferences ->
            preferences[FINISHED_SETUP] = true
        }
    }


}