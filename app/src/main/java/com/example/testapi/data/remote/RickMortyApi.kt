package com.example.testapi.data


import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickMortyApi {

    @GET("character")
    suspend fun getCharacter(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("species") species: String?,
        @Query("status") status: String?
    ): CharacterResponse


    @GET("character/{id}")
    suspend fun getCharacterProfileById(
        @Path("id") id: Int
    ): CharacterEntity


}