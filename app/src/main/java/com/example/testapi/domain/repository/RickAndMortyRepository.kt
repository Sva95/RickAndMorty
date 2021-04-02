package com.example.testapi.domain.repository


import androidx.paging.PagingData
import com.example.testapi.data.remote.model.CharacterApi
import com.example.testapi.domain.model.CharacterEntity
import com.example.testapi.util.CharacterFilter
import com.example.testapi.util.CharacterFilterCapsule
import kotlinx.coroutines.flow.Flow


interface RickAndMortyRepository {

    fun getSearchCharacters(): Flow<PagingData<CharacterEntity>>

    suspend fun getCharacterProfile(userId: Int): CharacterApi

    fun invalidateDataSource(filter: CharacterFilterCapsule)

}