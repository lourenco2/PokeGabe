package com.gabo.pokegabe.model

import com.google.gson.annotations.SerializedName

data class PokemonType(
    @SerializedName("type")
    val type: TypeName
)