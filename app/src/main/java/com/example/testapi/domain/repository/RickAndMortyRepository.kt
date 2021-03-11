package com.example.testapi.domain.repository


import androidx.paging.PagingData
import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.presentation.entity.CharacterUiEntity
import com.example.testapi.util.CharacterFilter
import com.example.testapi.util.CharacterFilterCapsule
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow


interface RickAndMortyRepository {

    suspend fun getSearchCharacters(): Flow<PagingData<CharacterEntity>>

    suspend fun getCharacterProfile(userId: Int): Flow<CharacterEntity>

    fun invalidateDataSource(filter: CharacterFilterCapsule)

}