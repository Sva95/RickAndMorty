package com.example.testapi.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.remote.CharacterPagingSource
import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.presentation.entity.CharacterUiEntity
import com.example.testapi.presentation.mapper.Mapper
import com.example.testapi.util.CharacterFilter
import com.example.testapi.util.CharacterFilterCapsule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RickAndMortyRepositoryImpl(
    private val api: RickMortyApi
) : RickAndMortyRepository {

    private var filter = CharacterFilterCapsule()
    private var dataPagingSource: CharacterPagingSource? = null

    override suspend fun getSearchCharacters(): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    movieApiService = api,
                    characterFilter = filter
                ).also {
                    dataPagingSource = it
                }
            }
        ).flow
    }

    override fun invalidateDataSource(filter: CharacterFilterCapsule) {
        this.filter = filter
        dataPagingSource?.invalidate()
    }

    override suspend fun getCharacterProfile(userId: Int): Flow<CharacterEntity> {
        return flow {
            val result = api.getCharacterProfileById(userId)
            emit(result)
        }
    }
}


