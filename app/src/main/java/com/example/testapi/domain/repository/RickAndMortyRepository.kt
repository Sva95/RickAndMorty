package com.example.testapi.domain.repository


import androidx.paging.PagingData
import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.util.CharacterFilter
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow


interface RickAndMortyRepository {

    suspend fun getCharacters(
        page: Int,
        characterFilter: CharacterFilter
    ): Flow<CharacterResponse>


    suspend fun getCharacterProfile(userId: Int): Flow<CharacterEntity>

}