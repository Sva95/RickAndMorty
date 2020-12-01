package com.example.testapi.data.repository


import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.domain.repository.RickAndMortyRepository
import com.example.testapi.util.CharacterFilter
import io.reactivex.Single

class RickAndMortyRepositoryImpl(private val api: RickMortyApi) : RickAndMortyRepository {


    override fun getCharacters(
        page: Int,
        characterFilter: CharacterFilter
    ): Single<CharacterResponse> {
        return api.getFilterUserNew(
            page = page,
            name = characterFilter.name,
            species = characterFilter.species,
            status = characterFilter.status
        )
    }
}


