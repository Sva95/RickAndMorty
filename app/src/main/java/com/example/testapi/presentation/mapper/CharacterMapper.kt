package com.example.testapi.presentation.mapper


import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.presentation.entity.CharacterEntity

class CharacterMapper : Mapper<CharacterResponse, List<CharacterEntity>> {

    override fun mapToEntity(type: CharacterResponse): List<CharacterEntity> {
        val listUsers = mutableListOf<CharacterEntity>()
        type.results.map {
            listUsers.add(
                CharacterEntity(
                    id = it.id,
                    name = it.name ?: "",
                    status = it.status ?: "",
                    species = it.species ?: "",
                    img_url = it.image ?: "",
                    location = it.location?.name ?: ""
                )
            )
        }
        return listUsers
    }
}