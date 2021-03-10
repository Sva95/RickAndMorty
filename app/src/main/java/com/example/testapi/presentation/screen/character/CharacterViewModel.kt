package com.example.testapi.presentation.screen.character

import androidx.lifecycle.*
import androidx.paging.*
import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.remote.CharacterPagingSource
import com.example.testapi.data.remote.model.CharacterEntity
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
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val movieRickMortyApi: RickMortyApi
) : ViewModel() {

    private var _dataPagingSource: CharacterPagingSource? = null
    private val filter = CharacterFilterCapsule()
    private val _requestChannel = ConflatedBroadcastChannel(filter.getFilter())

    val requestChannel: Flow<CharacterFilter> = _requestChannel.asFlow()

    private val charactersConfig = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 2
        ),
        pagingSourceFactory = {
            CharacterPagingSource(
                movieApiService = movieRickMortyApi,
                characterFilter = filter
            ).also {
                _dataPagingSource = it
            }
        }
    ).flow
        .map { it.map { characterEntityMapper.mapToEntity(it) } }
        .cachedIn(viewModelScope)

    val characters: Flow<PagingData<CharacterUiEntity>>
        get() = charactersConfig

    init {
        _requestChannel
            .asFlow()
            .debounce { 400 }
            .onEach { _dataPagingSource?.invalidate() }
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

