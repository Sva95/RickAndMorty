package com.example.testapi.domain.model


data class CharacterEntity(
    val id : Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
)