package com.example.testapi.domain.model

import com.example.testapi.data.local.CharacterDb
import com.example.testapi.data.remote.model.CharacterApi
import com.example.testapi.presentation.entity.CharacterProfileEntity


data class CharacterEntity(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
)


fun CharacterApi.toProfileUi(): CharacterProfileEntity {
    return CharacterProfileEntity(
        name = name,
        status = status,
        species = species,
        imgUrl = image,
        locationEntity = location.name,
        episode = episode
    )
}


