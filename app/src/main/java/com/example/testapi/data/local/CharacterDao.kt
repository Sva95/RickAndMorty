package com.example.testapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query(
        "SELECT * FROM character WHERE name LIKE :name"
    )
    suspend fun getCharactersOnPage(
        page: Int,
        name: String,
        status: String,
        species: String
    ): List<CharacterDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterDb>)
}