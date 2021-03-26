package com.example.testapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query(
        "SELECT * FROM character WHERE page LIKE :page" +
                "AND name LIKE :name " +
                "AND status LIKE :status" +
                "AND species LIKE :species"
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