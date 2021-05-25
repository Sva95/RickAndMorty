package com.example.testapi.presentation.screen.main

import androidx.lifecycle.*
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.presentation.entity.CharacterEntity
import com.example.testapi.presentation.mapper.Mapper
import com.example.testapi.util.CacheLiveData
import com.example.testapi.util.CharacterFilter
import com.example.testapi.util.CharacterFilterCapsule
import com.example.testapi.util.CharacterState

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class CharacterViewModel(
    private val characterEntityMapper: Mapper<CharacterResponse, List<CharacterEntity>>,
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {

    private var page = 1
    private var disposePagin: Disposable? = null
    private val characterSubject = PublishSubject.create<CharacterFilter>()
    private val filter = CharacterFilterCapsule()

    private val _characterCache = CacheLiveData()
    val character: LiveData<List<CharacterEntity>>
        get() = _characterCache

    private val _filterState = MutableLiveData<CharacterFilter>()
    val observeFilterState: LiveData<CharacterFilter>
        get() = _filterState

    private val _characterState = MutableLiveData<CharacterState>()
    val networkState: LiveData<CharacterState>
        get() = _characterState

    init {
        _filterState.value = filter.getFilter()
        initCharacterSubject()
    }

    fun updateCharacterName(userName: String) {
        if (userName.equals(filter.getFilter().name)) return
        filter.setFilterName(userName)
        this.page = 1
        updateWithFilter()
    }

    fun updateFilterSpecies(characterFilter: CharacterFilter) {
        filter.setFilterSpecies(characterFilter)
        updateWithFilter();
    }

    fun updateFilterStatus(characterFilter: CharacterFilter) {
        filter.setFilterStatus(characterFilter)
        updateWithFilter();
    }

    private fun updateWithFilter(){
        if (_characterState.value is CharacterState.Success || _characterState.value is CharacterState.Progress) {
            onLoadUpdateFilter()
        } else {
            _characterCache.clearCache()
            initCharacterSubject()
        }
    }

    fun onLoadMore() {
        characterSubject.onNext(filter.getFilter())
    }

    fun onRetry() {
        initCharacterSubject()
        onLoadUpdateFilter()
    }

    fun onRetryPaging() {
        initCharacterSubject()
    }

    private fun onLoadUpdateFilter() {
        _characterCache.clearCache()
        page = 1
        characterSubject.onNext(filter.getFilter())
    }

    private fun initCharacterSubject() {
        disposePagin?.dispose()
        disposePagin = characterSubject
            .startWith(filter.getFilter())
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .switchMap {
                rickAndMortyRepository.getCharacters(
                    page = page,
                    characterFilter = filter.getFilter()
                )
                    .toObservable()
                    .subscribeOn(Schedulers.io())
            }
            .map { characterEntityMapper.mapToEntity(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { if (page == 1) _characterState.value = CharacterState.Progress }
            .subscribe(
                { handleSuccess(it) },
                { handleError(it as Exception) }
            )
    }

    private fun handleSuccess(list: List<CharacterEntity>) {
        page = page + 1
        _characterState.value = CharacterState.Success
        _characterCache.value = list
    }

    private fun handleError(e: Exception) {
        when (e) {
            is UnknownHostException ->
                if (_characterCache.value.isEmpty()) {
                    _characterState.value = CharacterState.NetworkError
                } else {
                    _characterState.value = CharacterState.NetworkPagingError
                }

            is HttpException ->
                if (_characterCache.value.isEmpty()) {
                    _characterState.value = CharacterState.NotFound
                } else {
                    _characterState.value = CharacterState.NotFoundMore
                }
            is SocketTimeoutException -> _characterState.value = CharacterState.NetworkError
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposePagin?.dispose()
    }

    companion object {
        const val DEBOUNCE_TIME: Long = 400
    }

}