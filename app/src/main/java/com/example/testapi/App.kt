package com.example.testapi

import android.app.Application
import com.example.testapi.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }

    private val appComponent =
        listOf(
            repositoryModule,
            networkModule,
            characterPrefModule,
            databaseModule,
            viewModelModule
        )

}

