package com.gabo.pokegabe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabo.pokegabe.adapter.PokeAbilitiesAdapter
import com.gabo.pokegabe.adapter.PokeStatsAdapter
import com.gabo.pokegabe.adapter.PokeTypeAdapter
import com.gabo.pokegabe.network.PokeApi

import com.squareup.picasso.Picasso

class PokemonDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar()?.hide();

        val pokeName : TextView = findViewById(R.id.pokemonName)
        val recyclerViewStats : RecyclerView = findViewById(R.id.poke_recycler_stats)
        val recyclerViewType : RecyclerView = findViewById(R.id.poke_recycler_types)
        val recyclerViewAbilities : RecyclerView = findViewById(R.id.poke_recycler_abilities)

        val pokemonUrl = intent.getStringExtra("pokemonUrl")
        val pokemonId = this.getPokemonId(pokemonUrl!!)

        PokeApi().getPokemonById(pokemonId) { pokemon ->
            this.putImageOfPokemon(pokemonId)
            pokeName.text = pokemon?.name

            recyclerViewStats.adapter = PokeStatsAdapter(pokemon!!)
            recyclerViewStats.layoutManager = GridLayoutManager(this, 2)

            recyclerViewType.adapter = PokeTypeAdapter(pokemon, findViewById(R.id.pokeType))
            recyclerViewType.layoutManager = LinearLayoutManager(this)

            recyclerViewAbilities.adapter = PokeAbilitiesAdapter(pokemon, findViewById(R.id.pokeAbilities))
            recyclerViewAbilities.layoutManager = GridLayoutManager(this, 2)

        }
    }

    private fun putImageOfPokemon(id: String) {
        val imageView : ImageView = findViewById(R.id.pokemon_png)
        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        Picasso.get().load(url).into(imageView)
    }

    private fun getPokemonId(url: String): String {
        return url.replace("https://pokeapi.co/api/v2/pokemon/", "").replace("/","")
    }
}