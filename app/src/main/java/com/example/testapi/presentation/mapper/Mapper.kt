package com.example.testapi.presentation.mapper

interface Mapper<E, D> {
    fun mapToEntity(type: E): D
}