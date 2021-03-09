package com.example.testapi.presentation.screen.character

import androidx.lifecycle.*
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.presentation.entity.CharacterEntity
import com.example.testapi.presentation.mapper.Mapper
import com.example.testapi.util.*


import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CharacterViewModel(
    private val characterEntityMapper: Mapper<CharacterResponse, List<CharacterEntity>>,
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {

    private var page = 1
    private val filter = CharacterFilterCapsule()

/*    private val _characterCache = CacheLiveData()
    val character: LiveData<List<CharacterEntity>>
        get() = _characterCache*/

    private val _uiState =
        MutableStateFlow<CharacterProfileUiState>(CharacterProfileUiState.Loading)
    val uiState: StateFlow<CharacterProfileUiState> = _uiState


    private val _filterState = MutableLiveData<CharacterFilter>()
    val observeFilterState: LiveData<CharacterFilter>
        get() = _filterState

    private val _characterState = MutableLiveData<CharacterState>()
    val networkState: LiveData<CharacterState>
        get() = _characterState

    init {
        _filterState.value = filter.getFilter()
        onLoadUpdateFilter()
    }

    fun setFilterName(userName: String) {
        if (userName == filter.getFilter().name) return
        filter.updateFilterName(userName)
        onLoadUpdateFilter()
    }

    fun setFilterStatus(characterFilter: CharacterFilter) {
        filter.updateFilterStatus(characterFilter)
        onLoadUpdateFilter()
    }

    fun setFilterSpecies(characterFilter: CharacterFilter) {
        filter.updateFilterSpecies(characterFilter)
        onLoadUpdateFilter()
    }

    fun onLoadMore() {
        getCharacters(filter.getFilter())
    }

    fun onRetry() {
        onLoadUpdateFilter()
    }

    private fun onLoadUpdateFilter() {
     //   _characterCache.clearCache()
        page = 1
        getCharacters(characterFilter = filter.getFilter())
    }

    private fun getCharacters(characterFilter: CharacterFilter) {

    }

    private fun handleSuccess(list: List<CharacterEntity>) {
        page = page + 1
        _characterState.value = CharacterState.Success

        //_characterCache.value = list
    }

    private fun handleError(e: Exception) {
        println("Error")
        when (e) {
            is UnknownHostException -> println()
      /*          if (_characterCache.value.isEmpty()) {
                    _characterState.value = CharacterState.NetworkError
                } else {
                    _characterState.value = CharacterState.NetworkPagingError
                }*/

            is HttpException ->println()
           /*     if (_characterCache.value.isEmpty()) {
                    _characterState.value = CharacterState.NotFound
                } else {
                    _characterState.value = CharacterState.NotFoundMore
                }*/
            is SocketTimeoutException -> _characterState.value = CharacterState.NetworkError
        }
    }

}