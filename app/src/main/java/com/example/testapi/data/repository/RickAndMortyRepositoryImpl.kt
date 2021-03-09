package com.example.testapi.data.repository


import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.util.CharacterFilter
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class RickAndMortyRepositoryImpl(private val api: RickMortyApi) : RickAndMortyRepository {


    override fun getCharacters(
        page: Int,
        characterFilter: CharacterFilter
    ): Flow<CharacterResponse> {
        return api.getFilterUserNew(
            page = page,
            name = characterFilter.name,
            species = characterFilter.species,
            status = characterFilter.status
        )
    }

    override fun getCharacterProfile(userId: Int): Flow<CharacterEntity> {
        return api.getCharacterProfileById(userId)
    }
}


