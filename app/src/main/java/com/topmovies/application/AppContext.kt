package com.topmovies.application

import android.app.Application
import com.topmovies.application.KoinModules.dbModule
import com.topmovies.application.KoinModules.mainModule
import com.topmovies.application.KoinModules.movieModule
import org.koin.android.ext.android.startKoin

class AppContext : Application() {
    companion object {
        lateinit var instance: AppContext
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin()
    }

    private fun startKoin() {
        startKoin(this, listOf(
            mainModule,
            dbModule,
            movieModule
        ))
    }
}