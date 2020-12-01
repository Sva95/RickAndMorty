package com.example.testapi.util

import com.example.testapi.presentation.entity.CharacterEntity

sealed class CharacterState {
    object NetworkError : CharacterState()
    object NetworkPagingError : CharacterState()
    object Success : CharacterState()
    object Progress : CharacterState()
    object NotFound : CharacterState()
    object NotFoundMore : CharacterState()
}