package com.example.testapi.presentation.mapper

import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.presentation.entity.CharacterProfileEntity

class CharacterProfileMapper : Mapper<CharacterEntity, CharacterProfileEntity> {

    override fun mapToEntity(type: CharacterEntity): CharacterProfileEntity {

        return CharacterProfileEntity().apply {
            name = type.name
            status = type.status
            species = type.species
            imgUrl = type.image
            locationEntity = type.location.name
            episode = type.episode
        }
    }
}