package com.gabo.pokegabe.model

import com.google.gson.annotations.SerializedName

data class PokeResponse(
    @SerializedName("results")
    val pokemons: List<Pokemon>
)
