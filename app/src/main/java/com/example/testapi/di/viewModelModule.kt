package com.example.testapi.di


import com.example.testapi.presentation.mapper.CharacterMapper
import com.example.testapi.presentation.mapper.CharacterProfileMapper
import com.example.testapi.presentation.screen.character.CharacterViewModel
import com.example.testapi.presentation.screen.character_profile.CharacterProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        CharacterViewModel(
            movieRickMortyApi = get(),
            rickAndMortyRepository = get(),
            characterEntityMapper = CharacterMapper()
        )
    }

    viewModel { (characterId: Int) ->
        CharacterProfileViewModel(
            characterId = characterId,
            characterEntityMapper = CharacterProfileMapper(),
            rickAndMortyRepository = get()
        )
    }
}

