package com.example.testapi.presentation.screen.character_profile

import androidx.lifecycle.*
import com.example.testapi.domain.model.toProfileUi
import com.example.testapi.domain.repository.RickAndMortyRepository

import com.example.testapi.util.CharacterProfileUiState
import kotlinx.coroutines.*


class CharacterProfileViewModel(
    private val characterId: Int,
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<CharacterProfileUiState>()
    val uiState: LiveData<CharacterProfileUiState>
        get() = _uiState

    init {
        getCharacterById()
    }

    fun retry() {
        getCharacterById()
    }

    private fun getCharacterById() {
        viewModelScope.launch {
            _uiState.value = CharacterProfileUiState.Loading
            try {
                coroutineScope {
                    val user = async(Dispatchers.IO) {
                        rickAndMortyRepository.getCharacterProfile(characterId).toProfileUi()
                    }
                    _uiState.value = CharacterProfileUiState.Success(user.await())
                }
            } catch (e: Exception) {
                _uiState.value = CharacterProfileUiState.Error(e)
            }
        }
    }
}