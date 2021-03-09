package com.example.testapi.data


import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickMortyApi {

    @GET("character")
    fun getFilterUserNew(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("species") species: String?,
        @Query("status") status: String?
    ): Single<CharacterResponse>


    @GET("character/{id}")
    fun getCharacterProfileById(
        @Path("id") id: Int
    ): Flow<CharacterEntity>


}