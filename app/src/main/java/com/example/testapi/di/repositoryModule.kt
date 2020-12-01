package com.example.testapi.di


import com.example.testapi.data.repository.RickAndMortyRepositoryImpl
import com.example.testapi.domain.repository.RickAndMortyRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<RickAndMortyRepository> { RickAndMortyRepositoryImpl(api = get()) }
}