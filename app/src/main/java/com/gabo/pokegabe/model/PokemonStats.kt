package com.gabo.pokegabe.model

import com.google.gson.annotations.SerializedName

data class PokemonStats(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("stat")
    val stat: StatName
)
