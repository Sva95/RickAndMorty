package com.example.testapi.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.local.CharacterDao
import com.example.testapi.data.prefs.CharacterSharedPrefs
import com.example.testapi.data.remote.CharacterPagingSource
import com.example.testapi.data.remote.model.CharacterApi
import com.example.testapi.domain.model.CharacterEntity
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.util.CharacterFilter
import com.example.testapi.util.CharacterFilterCapsule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RickAndMortyRepositoryImpl(
    private val api: RickMortyApi,
    private val characterDao: CharacterDao,
    private val characterPrefs: CharacterSharedPrefs
) : RickAndMortyRepository {

    private var filter = CharacterFilterCapsule()
    private var dataPagingSource: CharacterPagingSource? = null

      override fun getSearchCharacters(): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    movieApiService = api,
                    characterFilter = filter,
                    characterDao = characterDao,
                    characterPrefs = characterPrefs
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

    override suspend fun getCharacterProfile(userId: Int): Flow<CharacterApi> {
        return flow {
            val result = api.getCharacterProfileById(userId)
            emit(result)
        }
    }
}


