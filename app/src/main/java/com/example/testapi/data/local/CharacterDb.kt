package com.example.testapi.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapi.data.remote.model.CharacterApi
import com.example.testapi.domain.model.CharacterEntity

@Entity(tableName = "character")
data class CharacterDb(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
    @ColumnInfo(name = "page") val page: Int
)

fun CharacterApi.toDomain(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
        location = location.name,
        gender = gender,
        origin = origin.name
    )
}

fun CharacterDb.toDomain(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
        location = location,
        gender = gender,
        origin = origin
    )
}


fun CharacterApi.toDb(page: Int): CharacterDb {
    return CharacterDb(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
        location = location.name,
        gender = gender,
        origin = origin.name,
        page = page
    )
}

