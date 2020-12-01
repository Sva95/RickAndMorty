package com.example.testapi.util

import androidx.lifecycle.MutableLiveData
import com.example.testapi.presentation.entity.CharacterEntity


class CacheLiveData : MutableLiveData<List<CharacterEntity>>() {

    private val cache = mutableListOf<CharacterEntity>()

    override fun postValue(value: List<CharacterEntity>?) {
        value?.let { cache.addAll(it) }
        super.postValue(cache)
    }

    override fun setValue(value: List<CharacterEntity>?) {
        value?.let { cache.addAll(it) }
        super.setValue(cache)
    }

    override fun getValue(): List<CharacterEntity> {
        return cache
    }

    fun clearCache() {
        cache.clear()
    }
}