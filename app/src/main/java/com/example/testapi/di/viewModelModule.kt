package com.example.testapi.di


import com.example.testapi.presentation.mapper.CharacterMapper
import com.example.testapi.presentation.screen.character.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        CharacterViewModel(
            rickAndMortyRepository = get(),
            characterEntityMapper = CharacterMapper()
        )
    }
}

