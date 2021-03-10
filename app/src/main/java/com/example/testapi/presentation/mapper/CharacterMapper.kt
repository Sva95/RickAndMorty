package com.example.testapi.presentation.mapper


import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.data.remote.model.CharacterResponse
import com.example.testapi.presentation.entity.CharacterUiEntity

class CharacterMapper : Mapper<CharacterEntity, CharacterUiEntity> {

    override fun mapToEntity(type: CharacterEntity): CharacterUiEntity {

        return CharacterUiEntity(
            id = type.id,
            name = type.name ?: "",
            status = type.status ?: "",
            species = type.species ?: "",
            img_url = type.image ?: "",
            location = type.location?.name ?: ""
        )
    }
}