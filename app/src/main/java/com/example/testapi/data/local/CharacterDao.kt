package com.example.testapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character WHERE page LIKE :page")
    suspend fun getCharactersOnPage(
        page: Int
    ): List<CharacterDb>


    @Query("SELECT * FROM character")
    suspend fun getCharacters(

    ): List<CharacterDb>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterDb>)
}