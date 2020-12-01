package com.example.testapi.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") val info : Info,
    @SerializedName("results") val results : List<CharacterEntity>
)