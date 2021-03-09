package com.example.testapi.presentation.entity

data class CharacterProfileEntity(
     var name: String = "",
     var status: String = "",
     var species: String = "",
     var imgUrl: String = "",
     var locationEntity: String = "",
     var episode: List<String> = emptyList()
)