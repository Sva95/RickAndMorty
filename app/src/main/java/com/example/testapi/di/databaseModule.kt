package com.example.testapi.di

import android.content.Context
import com.example.testapi.data.local.DatabaseProvider
import com.example.testapi.data.prefs.CharacterSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {
    single {
        DatabaseProvider(androidContext())
    }

    single {
        get<DatabaseProvider>().provideAppDatabase().characterDao()
    }
}