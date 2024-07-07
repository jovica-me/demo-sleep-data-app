package me.jovica.app.health

import android.app.Application
import android.health.connect.HealthConnectManager

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}