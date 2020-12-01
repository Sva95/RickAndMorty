package com.example.testapi.presentation.entity


data class CharacterEntity(
    val id: Int,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val img_url: String = "",
    val location: String = ""
)