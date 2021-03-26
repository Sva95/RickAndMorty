package com.example.testapi.presentation.screen.character

import androidx.lifecycle.*
import androidx.paging.*
import com.example.testapi.data.remote.model.CharacterApi
import com.example.testapi.domain.model.CharacterEntity
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.presentation.entity.CharacterUiEntity
import com.example.testapi.presentation.mapper.Mapper
import com.example.testapi.util.*


import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
class CharacterViewModel(
    private val characterEntityMapper: Mapper<CharacterEntity, CharacterUiEntity>,
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {

    private val filter = CharacterFilterCapsule()
    private val _requestChannel = ConflatedBroadcastChannel(filter.getFilter())
    val filterChannel: Flow<CharacterFilter> = _requestChannel.asFlow()

    private val _characters = MutableStateFlow<PagingData<CharacterUiEntity>?>(null)
     var characters: Flow<PagingData<CharacterUiEntity>> =
        _characters.flatMapLatest { rickAndMortyRepository.getSearchCharacters() }
            .map { it.map { characterEntityMapper.mapToEntity(it) } }
            .cachedIn(viewModelScope)

    init {
        _requestChannel
            .asFlow()
            .debounce { 150 }
            .onEach { rickAndMortyRepository.invalidateDataSource(filter) }
            .launchIn(viewModelScope)
    }

    fun setFilterName(userName: String) {
        if (userName == filter.getFilter().name) return
        filter.updateFilterName(userName)
        updateFilter()
    }

    fun setFilterStatus(characterFilter: CharacterFilter) {
        filter.updateFilterStatus(characterFilter)
        updateFilter()
    }

    fun setFilterSpecies(characterFilter: CharacterFilter) {
        filter.updateFilterSpecies(characterFilter)
        updateFilter()
    }

    private fun updateFilter() {
        viewModelScope.launch {
            _requestChannel.send(filter.getFilter())
        }
    }
}

