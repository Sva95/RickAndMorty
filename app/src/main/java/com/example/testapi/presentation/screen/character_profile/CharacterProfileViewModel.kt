package com.example.testapi.presentation.screen.character_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapi.data.remote.model.CharacterApi
import com.example.testapi.domain.repository.RickAndMortyRepository

import com.example.testapi.presentation.entity.CharacterProfileEntity
import com.example.testapi.presentation.mapper.Mapper
import com.example.testapi.util.CharacterProfileUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@OptIn(InternalCoroutinesApi::class)
class CharacterProfileViewModel(
    private val characterId: Int,
    private val characterEntityMapper: Mapper<CharacterApi, CharacterProfileEntity>,
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterProfileUiState>(CharacterProfileUiState.Empty)
    val uiState: StateFlow<CharacterProfileUiState> = _uiState

    init {
        getUserProfile(characterId)
    }

    fun retry() {
        getUserProfile(characterId)
    }

    fun getUserProfile(idUser: Int) {
        viewModelScope.launch {
            rickAndMortyRepository.getCharacterProfile(idUser)
                .map { characterEntityMapper.mapToEntity(it) }
                .flowOn(Dispatchers.IO)
                .catch { handleError(it as Exception) }
                .collect { handleSuccess(it) }
        }
    }

    private fun handleError(e: Exception) {
        _uiState.value = CharacterProfileUiState.Error(e)
    }

    private fun handleSuccess(characterId: CharacterProfileEntity) {
        _uiState.value = CharacterProfileUiState.Success(characterId)
    }
}