package com.example.testapi

import android.app.Application
import com.example.testapi.di.networkModule
import com.example.testapi.di.repositoryModule
import com.example.testapi.di.viewModelModule
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
            networkModule,
            viewModelModule,
            repositoryModule
        )

}

