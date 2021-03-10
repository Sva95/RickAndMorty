package com.example.testapi.data.repository


import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.util.CharacterFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RickAndMortyRepositoryImpl(private val api: RickMortyApi) : RickAndMortyRepository {

    override suspend fun getCharacters(
        page: Int,
        characterFilter: CharacterFilter
    ): Flow<CharacterResponse> {
        return flow {
            val result = api.getCharacter(
                page = page,
                name = characterFilter.name,
                species = characterFilter.species,
                status = characterFilter.status
            )
            emit(result)
        }
    }


    override suspend fun getCharacterProfile(userId: Int): Flow<CharacterEntity> {
        return flow {
            val result = api.getCharacterProfileById(userId)
            emit(result)
        }
    }
}


