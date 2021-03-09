package com.example.testapi.util

import com.example.testapi.presentation.entity.CharacterProfileEntity

sealed class CharacterProfileUiState {
    data class Success(val profile: CharacterProfileEntity): CharacterProfileUiState()
    data class Error(val exception: Throwable): CharacterProfileUiState()
    object Loading: CharacterProfileUiState()
}