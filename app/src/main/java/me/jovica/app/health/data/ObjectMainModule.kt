package me.jovica.app.health.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ObjectMainModule {

    @Provides
    @Singleton
    fun settingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }

    @Provides
    @Singleton
    fun healthConnectManager(@ApplicationContext context: Context): HealthConnectManager {
        return HealthConnectManager(context)
    }
}