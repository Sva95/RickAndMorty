package com.example.testapi.data.remote.model

import com.google.gson.annotations.SerializedName

data class OriginEntity(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)