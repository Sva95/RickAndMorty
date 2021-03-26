package com.example.testapi.di

import android.content.Context
import com.example.testapi.data.prefs.CharacterSharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val characterPrefModule = module {
    single {
        val prefs = androidContext().getSharedPreferences("characters", Context.MODE_PRIVATE)
        CharacterSharedPrefs(prefs)
    }
}